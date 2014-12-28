package com.midgetontoes.todolist.controller;

public final class ResourceMapping {
    private Class<?> controllerClass;
    private String resourceName;

    public ResourceMapping(Class<?> controllerClass, String resourceName) {
        this.controllerClass = controllerClass;
        this.resourceName = resourceName;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public String getResourceName() {
        return resourceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceMapping that = (ResourceMapping) o;

        if (!controllerClass.equals(that.controllerClass)) return false;
        if (!resourceName.equals(that.resourceName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = controllerClass.hashCode();
        result = 31 * result + resourceName.hashCode();
        return result;
    }
}
