angular.module('todoListApp', ['restangular'])
  .controller('MainCtrl', ['$scope', 'hateoasClientFactory', function($scope, hateoasClientFactory) {
    hateoasClientFactory.build('/').then(function(client) {
      client.lists.getList().then(function(lists) {
        lists[0].items().then(function(items) {
          console.log(items[0]);
        });
      });
    });
  }])
  .factory('HateoasRestangular', ['Restangular', function(Restangular) {
    return Restangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.setRestangularFields({
        selfLink: "_links.self.href"
      });
      RestangularConfigurer.setOnElemRestangularized(function(elem, isCollection, what, Restangular) {
        for (var rel in elem._links) {
          if (rel !== 'curies') {
            var index = rel.indexOf(':');
            var name = rel.substring(index + 1);
            elem.addRestangularMethod(name, 'getList', elem._links[rel].href);
          }
        }
        return elem;
      });
      RestangularConfigurer.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
        if (_.has(data, "_embedded")) {
          return _.values(data._embedded)[0];
        }
        return data;
      });
    });
  }])
  .factory('hateoasClientFactory', ['$http', '$q', 'HateoasRestangular', function($http, $q, HateoasRestangular) {
    function HateoasClient(data) {
      this._parse(data);
    }

    HateoasClient.prototype._parse = function(data) {
      for (var rel in data._links) {
        if (rel === 'curies') {
          this.curies = data._links[rel];
        }
        else {
          var index = rel.indexOf(':');
          var resourceName = rel.substring(index + 1);
          this[resourceName] = HateoasRestangular.allUrl(resourceName, data._links[rel].href);
        }
      }
    };

    return {
      build: function(rootUrl) {
        var deferred = $q.defer();
        $http.get(rootUrl).success(function(data) {
          deferred.resolve(new HateoasClient(data));
        }).error(function(msg) {
          deferred.reject(msg);
        });
        return deferred.promise;;
      }
    };
  }]);
