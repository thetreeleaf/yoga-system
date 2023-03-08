public class SequoiadPrinter {

    public void sequoia() {
        for (int i = 0; i < 10; i++) {
            synchronized (this) {
                System.out.print("Sequoia");
                try {
                    notify();
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void db() {
        for (int i = 0; i < 10; i++) {
            synchronized (this) {
                System.out.println("DB");
                try {
                    notify();
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SequoiadPrinter sdb = new SequoiadPrinter();
        Thread t1 = new Thread(sdb::sequoia);
        Thread t2 = new Thread(sdb::db);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
