package currency.converter.project_interface;

public class Currency
{
    private final int id;       // числовой код
    private final String code;  // буквенный код
    private final String name;  // полное название

    public Currency(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getId() { return id; }

    @Override
    public String toString() {
        return id + " - " + name; // отображение в ComboBox
    }
}
