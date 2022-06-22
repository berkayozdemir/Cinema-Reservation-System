public class User {
    String username;
    String hashedpass;
    boolean clubmember;
    boolean admin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedpass() {
        return hashedpass;
    }

    public void setHashedpass(String hashedpass) {
        this.hashedpass = hashedpass;
    }

    public boolean isClubmember() {
        return clubmember;
    }

    public void setClubmember(boolean clubmember) {
        this.clubmember = clubmember;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User(String username, String hashedpass, boolean clubmember, boolean admin) {
        this.username = username;
        this.hashedpass = hashedpass;
        this.clubmember = clubmember;
        this.admin = admin;
    }
}
