package metaroa.traumimages.dto;

import metaroa.traumimages.Roles.Roles;

public class AssignRoleDTO {
    private Long userId;
    private Roles.RolesList role;

    public Long getUserId() {
        return userId;
    }

    public Roles.RolesList getRole() {
        return role;
    }

    public void setRole(Roles.RolesList role) {
        this.role = role;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
