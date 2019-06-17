package Model;

public class User {
    private String id;
    private String username;
    private  String phone;

    public User(String id, String usernname, String phone) {
        this.id = id;
        this.username = usernname;
        this.phone = phone;
    }

    public User()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
