package abvtools.enumeration;

public enum SistemasOperacionais {

    WINDOWS(1),
    LINUX(2),
    MAC(3);

    private final int id;

    private SistemasOperacionais(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static int get() {
        String so = System.getProperty("os.name");

        so = so.toUpperCase();

        if (so.contains(SistemasOperacionais.WINDOWS.toString())) {
            return SistemasOperacionais.WINDOWS.getId();

        } else if (so.contains(SistemasOperacionais.LINUX.toString())) {
            return SistemasOperacionais.LINUX.getId();

        } else {
            return SistemasOperacionais.MAC.getId();
        }

    }

}
