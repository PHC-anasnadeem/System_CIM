package com.phc.cim.DataElements;


public class Role {


    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(String role_Id) {
        Role_Id = role_Id;
    }

    String RoleName;
    String Role_Id;
   public Role(String Role_Id, String RoleName){
       this.Role_Id=Role_Id;
    this.RoleName=RoleName;

   }
}
