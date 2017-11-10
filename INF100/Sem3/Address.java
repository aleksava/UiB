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

    // Empty constructor and method for each variable to simplfy
    // creation of addresses
    public Address() {
        // empty
    }

    public void addStreet(String s){
        street = s;
    }

    public void addStreetNum(int sn){
        streetNumber = sn;
    }

    public void addPostCode(int pc){
        postalCode = pc;
    }

    public void addTown(String t){
        town = t;
    }
    public void addCountry(String c){
        country = c;
    }
    
    @Override
    public String toString() {
        return (street + " " + streetNumber + "\n"
                + postalCode + ", " + town + "\n"
                + country);
    }
}
