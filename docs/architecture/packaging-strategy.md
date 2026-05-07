# Packaging Strategy: From Layers to Features

## 📦 The Shift

We have migrated the project from **Packaging by Layer** to **Packaging by Feature**.

### ❌ Layer-based Packaging (Obsolete)

In this model, files are grouped by their technical role.

- `co.com.empresa.domain.model`
- `co.com.empresa.domain.services`
- `co.com.empresa.domain.repository`
- `co.com.empresa.application.dto`
- `co.com.empresa.application.usecases`

**Problem:** To implement a single change in the "Type" feature, a developer has to open 5 different folders across the
whole project.

### ✅ Feature-based Packaging (Current)

In this model, files are grouped by the business feature they belong to.

- `co.com.empresa.domain.type` (contains Model, Service, Port)
- `co.com.empresa.application.type` (contains UseCase, DTO, Mapper)
- `co.com.empresa.infrastructure.type` (contains Controller, RepositoryImpl, ServiceImpl)

**Benefit:** High **Cohesion**. Everything related to "Type" is logically grouped together. This significantly reduces
the time spent navigating the project structure.

## 🛠️ Implementation Guide

When adding a new feature (e.g., `User`):

1. Create `domain.user` $\rightarrow$ Add `User.java`, `UserService.java`, `IUserRepository.java`.
2. Create `application.user` $\rightarrow$ Add `UserUseCase.java`, `UserDTO.java`, `UserMapper.java`.
3. Create `infrastructure.user` $\rightarrow$ Add `UserController.java`, `UserRepositoryImpl.java`,
   `UserServiceImpl.java`.
4. Create `infrastructure.entities.user` $\rightarrow$ Add `UserEntity.java`.
