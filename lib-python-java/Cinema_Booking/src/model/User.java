package model;

public class User {
    private String userId;
    private String username;
    private String password;
    private String name;
    private String address;
    private String contact;
    static int id = 1;


    public User(String userId, String username, String password, String name, String address, String contact) {
        this.userId = String.format("%03d", id++);
        setUsername(username);
        setPassword(password);
        setName(name);
        setAddress(address);
        setContact(contact);
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null) {
            this.username = username;
        }
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        if (password.length() == 6) {
            this.password = password;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = username;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != null) {
            this.address = address;
        }
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        if (contact.length() == 10) {
            this.contact = contact;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId: '" + userId + '\'' +
                ", username: '" + username + '\'' +
                ", password (6 ký tự) :'[PROTECTED]'" +
                ", name :'" + name + '\'' +
                ", address :'" + address + '\'' +
                ", contact :'" + contact + '\'' +
                '}';
    }
}
