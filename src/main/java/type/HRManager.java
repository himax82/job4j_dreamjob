package type;

public class HRManager {

    private int id;
    private String name;
    private String nameCompany;

    public HRManager(int id, String name, String nameCompany) {
        this.id = id;
        this.name = name;
        this.nameCompany = nameCompany;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }
}
