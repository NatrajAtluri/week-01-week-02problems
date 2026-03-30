class ParkingLot {
    private String[] table;
    private int size;

    public ParkingLot(int capacity) {
        table = new String[capacity];
        size = capacity;
    }

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % size;
    }

    public int park(String plate) {
        int idx = hash(plate);

        for (int i = 0; i < size; i++) {
            int newIdx = (idx + i) % size;
            if (table[newIdx] == null) {
                table[newIdx] = plate;
                return newIdx;
            }
        }
        return -1;
    }

    public void exit(String plate) {
        for (int i = 0; i < size; i++) {
            if (plate.equals(table[i])) {
                table[i] = null;
                return;
            }
        }
    }
}