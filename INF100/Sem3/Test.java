

class Test {
    public static void main (String[] args) {
        String street = "Fake Street";
        int streetNumber = 123;
        int postalCode = 32145;
        String town = "Bergen";
        String country = "Norway";
        Address senderAddress = new Address(street, streetNumber,
                                            postalCode, town, country);
        String senderName = "Dr. Java";
        Person sender = new Person(senderName, senderAddress);
        street = "News Street";
        streetNumber = 999;
        postalCode = 32145;
        town = "Bergen";
        country = "Norway";
        Address recipientAddress = new Address(street, streetNumber,
                                               postalCode, town, country);
        String recipientName = "Mr. Python";
        Person recipient = new Person(recipientName, recipientAddress);
        Parcel parcel = new Parcel(sender, recipient);
        System.out.println(parcel);
    } 
}
