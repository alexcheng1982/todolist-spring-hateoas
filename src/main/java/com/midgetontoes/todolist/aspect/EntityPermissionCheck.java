package com.midgetontoes.todolist.aspect;

import com.midgetontoes.todolist.AccessDeniedException;
import com.midgetontoes.todolist.EntityNotFoundException;
import com.midgetontoes.todolist.model.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EntityPermissionCheck {

    @Around("execution(public com.midgetontoes.todolist.model.List com.midgetontoes.todolist.service.ListService.*(..))")
    public Object checkListAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object entity = joinPoint.proceed();
        List list = (List) entity;
        if (list != null && list.getUser() != null
                && list.getUser().getUsername() != null
                && list.getUser().getUsername().equals(getCurrentUser())) {
            return list;
        }
        else if (list == null) {
            throw new EntityNotFoundException();
        }
        else throw new AccessDeniedException();
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            User user = (User) authentication.getPrincipal();
            return user.getUsername();
        }
        return null;
    }
}
