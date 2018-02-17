public class UserManagement {
    private static final String TABLE_USER = "user";
    private static final String TABLE_TEACHING = "teaching";
    private static final String TABLE_ROLE = "role";

    public void InsertUser (User pUser){
        boolean exist = ExistUser(pUser);
        String sql = "INSERT INTO "+ UserManagement.TABLE_USER +"...";
    }

    public void UpdateUser (User pUser){
        boolean exist = ExistUser(pUser);
        String sql = "UPDATE "+ UserManagement.TABLE_USER+"...";
    }

    public void DeleteUser (User pUser){
        boolean exist = ExistUser(pUser);
        String sql = "DELETE FROM "+ UserManagement.TABLE_USER+"...";
    }

    public void ExistUser (User pUser){
        String sql = "SELECT * FROM "+ UserManagement.TABLE_USER+"...";
    }

    public boolean checkMandatoryFieldUser(User pUser){
        return pUser.getMandatory();
    }

    public void InsertTeaching (Teaching pTeaching){
        String sql = "INSERT INTO "+UserManagement.TABLE_TEACHING+"...";
    }

    public void UpdateTeaching (Teaching pTeaching){
        String sql = "UPDATE "+ UserManagement.TABLE_TEACHING+"...";
    }

    public void DeleteTeaching (Teaching pTeaching){
        String sql = "DELETE FROM "+UserManagement.TABLE_TEACHING+"...";
    }

    public boolean checkMandatoryFieldTeaching(Teaching pTeaching){
        return pTeaching.getMandatory();
    }

    public void InsertRole (Role pRole){
        String sql = "INSERT INTO "+ UserManagement.TABLE_ROLE+"...";
    }

    public void DeleteRole (Role pRole){
        String sql = "DELETE FROM "+ UserManagement.TABLE_ROLE+"...";
    }

    public boolean checkMandatoryFieldRole(Role pRole){
        return pRole.getMandatory();
    }
}