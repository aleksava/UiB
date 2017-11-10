import java.util.UUID;

class Parcel {
    private Person sender;
    private Person recipient;
    private UUID barcode;

    public Parcel(Person snd, Person rec) {
        sender = snd;
        recipient = rec;
        barcode = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return ("Barcode: " + barcode + "\n" +
                "Sender: " + sender + "\n" +
                "Recipient: " + recipient);
    }
}
