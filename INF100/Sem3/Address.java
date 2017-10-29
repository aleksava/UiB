class Address {
    private String street;
    private int streetNumber;
    private int postalCode;
    private String town;
    private String country;

    public Address(String s, int sN, int pC, String t, String c) {
        street = s;
        streetNumber = sN;
        postalCode = pC;
        town = t;
        country = c;
    }

    @Override
    public String toString() {
        return (street + " " + streetNumber + "\n"
                + postalCode + ", " + town + "\n"
                + country);
    }
}
