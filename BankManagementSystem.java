package bank;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Bank{
    final String name = "UET BANK OF PAKISTAN";
    private Client[] ClList;
    private Account[] AcList;
    private int clientIndex, accountIndex;

    File file = new File("U:/users/JavaProjects/Java/src/bank/source");

    Bank(){
        ClList = new Client[300];
        AcList = new Account[500];
        this.clientIndex = 0;
        this.accountIndex = 0;

        File[] txtFiles = file.listFiles(((dir, name1) -> name1.endsWith(".txt")));

        if(txtFiles == null || txtFiles.length == 0) {
            try {
                File accounts = new File(file,"Accounts.txt");
                accounts.createNewFile();
            } catch (IOException e) {
                System.out.println();
            }
            try {
                File accounts = new File(file,"Clients.txt");
                accounts.createNewFile();
            } catch (IOException e) {
                System.out.println();
            }
        }
        else{
            boolean foundAccount= false, foundClient = false;
            for(File item: txtFiles){
                if(item.getName().equals("Accounts.txt")){
                    foundAccount = true;
                }
                if(item.getName().equals("Clients.txt")){
                    foundClient = true;
                }
                if(foundClient && foundAccount) break;
            }
            if(!foundAccount){
                File accounts = new File(file,"Accounts.txt");
                try {
                    accounts.createNewFile();
                }
                catch (IOException e) {
                    System.out.println("Some Error Occur.");
                }
            }
            if(!foundClient){
                File accounts = new File(file,"Clients.txt");
                try {
                    accounts.createNewFile();
                }
                catch (IOException e) {
                    System.out.println("Some Error Occur.");
                }
            }
            else{
                try{
                    FileReader clientFileReader = new FileReader(new File(file,"Clients.txt"));
                    Scanner sc = new Scanner(clientFileReader);
                    String clientData = "";
                    while (sc.hasNextLine()){
                        clientData += sc.nextLine() + "\n";
                    }
                    clientFileReader.close();
                    String[] dataClient = null;
                    if(!clientData.isEmpty()) {
                        clientData = clientData.substring(0, clientData.length() - 1);
                        dataClient = clientData.split("\n");
                    }
                    else JOptionPane.showMessageDialog(null,"No Client and Account Created Yet.");
                    if(dataClient != null) {
//                        for (String item : this.dataClient) {
//                            System.out.println(item);
//                        }
                        loadClientData(dataClient);
                    }
//                    else for(Client c: this.ClList) System.out.println(c);


                    FileReader accountFileReader = new FileReader(new File(file, "Accounts.txt"));
                    Scanner Sc = new Scanner(accountFileReader);
                    String accountData = "";
                    while (Sc.hasNextLine()){
                        accountData += Sc.nextLine() + "\n";
                    }
                    accountFileReader.close();
                    String[] dataAccount = null;
                    if(!accountData.isEmpty()) {
                        accountData = accountData.substring(0, accountData.length() - 1);
                        dataAccount = accountData.split("\n");
                    }
                    if(dataAccount != null) {
//                        for (String item : this.dataAccount) {
//                            System.out.println(item);
//                        }
                        loadAccountData(dataAccount);
                    }

                    FileReader indexReader = new FileReader(new File(file,"index.txt"));
                    Scanner indexR = new Scanner(indexReader);
                    String index = "";
                    while (indexR.hasNextLine()) index += indexR.nextLine()+"\n";
                    indexReader.close();
                    String[] indexes;
                    indexes = index.split("\n");
                    Client.count = Integer.parseInt(indexes[0]);
                    Account.count = Integer.parseInt(indexes[1]);
                }
                catch (IOException e){
                    System.out.println("Some Error occur");
                }
            }
        }
    }

    public void loadAccountData(String[] data){
        String[] accountDetails;
        Account a;
        for(int i = 0; i<data.length; i++){
            if(!data[i].isEmpty()){
                accountDetails = data[i].split(",");
                if(accountDetails.length != 0){
                    for(Client c: this.ClList){
                        if(c != null && c.getId().equals(accountDetails[2])) {
                            a = new Account(accountDetails[0],Float.parseFloat(accountDetails[1]),c);
                            this.AcList[i] = a;
                            accountIndex++;
                        }
                    }
                }
            }
        }
//        System.out.println(accountIndex);


//        for (Account item : this.AcList) {
//            if(item != null) {
//                System.out.println(item.getNumber() +
//                        "," + item.getAmount() + "," +
//                        item.getACholder().getId());
//            }
//
//        }
    }

    public void loadClientData(String[] data){
//        for (String item : data) {
//            System.out.println(item);
//        }
        String[] clients, account;
        Client c;
        Account a;
        String[] acc;
        Person p = new Person();
        for(int i = 0; i<data.length; i++){
            if(!data[i].isEmpty()) {
                clients = data[i].split(",");
                p.setName(clients[1]);
                p.setCNIC(clients[2]);
                p.setPhoneNo(clients[3]);
                c = new Client(clients[0], p);
                account = clients[4].substring(1, clients[4].length() - 2).split("\\|");
                for(String item: account){
                    if(item != null) {
                        acc = item.split("\\^");
                        if(acc.length != 0) {
                            a = new Account(acc[0], Float.parseFloat(acc[1]), c);
                            c.addAccount(a);
                        }
                    }
                }
                this.ClList[i] = c;
                clientIndex++;
            }
        }


//        for (Client item : this.ClList) {
//            if(item != null) {
//                String accountName = "";
//                for(Account ac: item.getAcList()){
//                    if(ac != null){
//                        accountName += ac.getNumber() + ",";
//                    }
//                }
//                System.out.println(item.getId() +
//                        "," + item.getPersonDetails().getName() + "," +
//                        item.getPersonDetails().getCNIC() + "," +
//                        item.getPersonDetails().getPhoneNo() + ","+accountName);
//            }
//
//        }
    }


    public void ClientAndAccount(){
        try {
            FileWriter fileWriterClient = new FileWriter(new File(file,"Clients.txt"));
            for (int i = 0; i < clientIndex; i++) {
                String client;
                if (ClList[i] != null) {
                    StringBuilder ac = new StringBuilder();
                    for(int j=0; j<ClList[i].getAcList().length; j++){
                        if(ClList[i].getAcList()[j] != null) {
                            ac.append(ClList[i].getAcList()[j].getNumber()).append("^").append(ClList[i].getAcList()[j].getAmount()).append("^").append(ClList[i].getAcList()[j].getACholder().getId()).append("|");
                        }
                    }
                    if(i == 0) client = ClList[i].getId()+","+ClList[i].getPersonDetails().getName()+","+ClList[i].getPersonDetails().getCNIC()+","+ClList[i].getPersonDetails().getPhoneNo()+",["+ac+"]";
                    else client = "\n"+ClList[i].getId()+","+ClList[i].getPersonDetails().getName().toUpperCase()+","+ClList[i].getPersonDetails().getCNIC()+","+ClList[i].getPersonDetails().getPhoneNo()+",["+ac+"]";
                    fileWriterClient.write(client);
                }
            }
            fileWriterClient.close();

            FileWriter fileAccount = new FileWriter(new File(file,"Accounts.txt"));
            String account;
            for (int i = 0; i<accountIndex; i++){
                if(AcList[i] != null){
                    if(i==0) account = AcList[i].getNumber()+","+AcList[i].getAmount()+","+AcList[i].getACholder().getId();
                    else account = "\n"+AcList[i].getNumber()+","+AcList[i].getAmount()+","+AcList[i].getACholder().getId();
                    fileAccount.write(account);
                }
            }
            fileAccount.close();

            FileWriter fileIndex = new FileWriter(new File(file,"index.txt"));
            fileIndex.write(Client.count+"\n"+Account.count);
            fileIndex.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    void addClient(Person P){//Completed
        Client c = new Client();
        Account a = new Account();
        c.setPersonDetails(P);
        a.setAmount(0);
        a.setACholder(c);
        c.addAccount(a);
        ClList[this.clientIndex++] = c;
        AcList[this.accountIndex++] = a;
        JOptionPane.showMessageDialog(null, "Client added successfully.","Info", JOptionPane.INFORMATION_MESSAGE);
    }//completed

    Account addAccount(String id, float amount, Client c){
        Account a = new Account(amount, c);
        c.addAccount(a);
        if(c.getAccountCreated()){
            AcList[accountIndex++] = a;
        }
//        ClientAndAccount();
        return a;
    }//completed

    Account searchAccount(String id){
        for (Account a: AcList){
            if(a != null && id.equals(a.getNumber())) return a;
        }
        return null;
    }//completed

    Boolean removeClient(String Id){
        int co = 0;
        int Index = 0;
        boolean remove = false;
        for(int i=0; i<ClList.length; i++){
            if(ClList[i] != null && Id.equals(ClList[i].getId())){
                for (int j=0; j< AcList.length; j++){
                    if(AcList[j] != null && AcList[j].getACholder().getId().equals(Id)){
                        AcList[j] = null;
                        if(co == 0) co += j;
//                        accountIndex--;
                    }
                }
                ClList[i] = null;
//                clientIndex--;
                remove = true;
                Index = i;
                break;
            }
        }
        if(remove) {
            for (int i = Index; i < clientIndex; i++) {
                ClList[i] = ClList[i + 1];
                ClList[i+1] = null;
            }
            for (int i = co; i< accountIndex; i++){
                AcList[i] = AcList[i+1];
                AcList[i+1] = null;
            }
            clientIndex--;
            accountIndex--;
            return true;
        }
        return false;
    }//completed

    float totalAmount(){
        float amount = 0;
        for(int i = 0; i<clientIndex; i++){
            if(ClList[i] != null) amount += ClList[i].totalAmount();
        }
        return amount;
    }//completed

    Client searchCustomerDetail(String CNIC){
        for (Client client : ClList) {
            if (client != null && CNIC.equals(client.getPersonDetails().getCNIC())) return client;
        }
        return null;
    }//completed

    public String toString(){return "";}

    public Client[] getClList() {
        return ClList;
    }

    public void setClList(Client[] clList) {
        ClList = clList;
    }

    public Account[] getAcList() {
        return AcList;
    }

//    public void setAcList(Account[] acList) {
//        AcList = acList;
//    }

//    public int getAccountIndex() {
//        return accountIndex;
//    }

//    public void setAccountIndex(int accountIndex) {
//        this.accountIndex = accountIndex;
//    }

//    public int getClientIndex() {
//        return clientIndex;
//    }

//    public void setClientIndex(int clientIndex) {
//        this.clientIndex = clientIndex;
//    }

}

class Account{
    private String number;
    private float amount;
    private Client ACholder;
    static int count = 0;

    Account(){
        ++count;
        number = "A"+count;
    }
    Account(float amount, Client ACholder){
        count++;
        number = "A"+count;
        this.amount = amount;
        this.ACholder = ACholder;
    }
    Account(String id, float amount, Client ACholder){
        this.amount = amount;
        this.number = id;
        this.ACholder = ACholder;
    }

    public float withdraw(float amount){
        if(this.amount >= amount){
            this.amount -= amount;
            return this.amount;
        }
        return -1;
    }//completed

    public float deposit(float amount){
        this.amount += amount;
        return this.amount;
    }//completed

    public String toString(){return "ID: " + ACholder.getId() + "\nName: " + ACholder.getPersonDetails().getName() + "\nNumber: " + number + "\nAmount: " + amount;}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Client getACholder() {
        return ACholder;
    }

    public void setACholder(Client ACholder) {
        this.ACholder = ACholder;
    }
}

class Client {
    private String Id;
    private Person PersonDetails;
    private Account[] AcList;
    static int count;
    private boolean accountCreated = false;

    Client(){
        ++count;
        this.Id = "C"+count;
        AcList = new Account[]{null, null, null, null, null};
    }


    Client(String Id, Person PersonDetails){
        this.Id = Id;
        this.PersonDetails = PersonDetails;
        AcList = new Account[5];
    }

    float totalAmount(){
        float amount = 0;
        for(Account a: AcList){
            if(a != null) amount += a.getAmount();
        }
        return amount;
    }//completed

    void withdraw(float amount, String accNo){
        Account A = null;
        for (Account a: AcList){
            if(a != null && accNo.equals(a.getNumber())){
                A = a;
                break;
            }
        }
        float AMOUNT = 0;
        if(A != null) AMOUNT = A.withdraw(amount);
        if(AMOUNT >= 0) JOptionPane.showMessageDialog(null, "Amount withdraw successfully. \nCurrent Amount is " + AMOUNT + ".","Info",JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(null, "Not Enough Amount.","Error",JOptionPane.ERROR_MESSAGE);
    }//completed

    void deposit(float amount, String accNo){
        Account A = null;
        for (Account a: AcList){
            if(a != null && accNo.equals(a.getNumber())){
                A = a;
                break;
            }
        }
        float AMOUNT = 0;
        if(A != null && amount>0) {
            AMOUNT = A.deposit(amount);
            JOptionPane.showMessageDialog(null, "Amount deposit successfully. \nCurrent Amount is "+AMOUNT+".","Info",JOptionPane.INFORMATION_MESSAGE);
        }
        else JOptionPane.showMessageDialog(null, "Amount must be greater than 0.","Error",JOptionPane.ERROR_MESSAGE);
    }//completed

    void addAccount(Account a){
        for(int i=0; i<5; i++){
            if(AcList[i] == null){
                AcList[i] = a;
                accountCreated = true;
                return;
            }
        }
        JOptionPane.showMessageDialog(null,"Client has already reached the maximum account containing limit.");
    }//completed


    public String toString(){
        String accountDetail = "";
        int i = 1;
        for(Account a: AcList){
            if(a != null){
                accountDetail += "Account ID "+i+":\t"+a.getNumber()+"\nAmount:\t"+a.getAmount()+"\n";
                i++;
            }
        }
        return "ID: " + Id + "\nName:\t"+
            PersonDetails.getName() + "\nCNIC:\t" +
            PersonDetails.getCNIC() + "\nPhone No.:\t" +
            PersonDetails.getPhoneNo()+"\n"+accountDetail;}

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Person getPersonDetails() {
        return PersonDetails;
    }

    public void setPersonDetails(Person personDetails) {
        PersonDetails = personDetails;
    }

    public Account[] getAcList() {
        return AcList;
    }

    public void setAcList(Account[] acList) {
        AcList = acList;
    }

    public boolean getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(boolean accountCreated) {
        this.accountCreated = accountCreated;
    }
}

class Person{
    private String Name, CNIC, phoneNo;

    Person(){
        this.Name = "";
        this.CNIC = "";
        this.phoneNo = "";
    }

    Person(String Name, String CNIC, String phoneNo){
        this.Name = Name;
        this.CNIC = CNIC;
        this.phoneNo = phoneNo;
    }


    public String toString(){return "";}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}

public class BankManagementSystem implements ActionListener {
    static JButton withdrawButton, depositButton, goSearchClient, searchAccountButton, searchClientButton, removeClientButton, totalAmountButton, addClientButton, addAccountButton, goAddClient, goAddAccount, goRemoveClient, goWithdraw, goDeposit, goSearchAccount;
    static JPanel showClientPanel, showAccountPanel ,menuPanel, depositPanel, withdrawPanel, searchClientPanel, searchAccountPanel, removeClientPanel, addClientPanel, addAccountPanel;
    private Bank bank;
    static JLabel name, cnicLabel, addAccountAmountLabel, phoneLabel, clientID, searchClientIDLabel, searchAccountIDLabel, removeClientIdLabel, accountDepositLable, amountDepositLabel, accountWithdrawLable, amountWithdrawLabel;
    static JTextField username, cnicField, addAccountAmountField, phoneField, clientIdField, searchClientIDField, searchAccountIDField, removeClientIdField, accountWithdrawField, amountWithdrawField, accountDepositField, amountDepositField;
    Client c;
    public static void main(String[] args) {
        BankManagementSystem gui = new BankManagementSystem();
        gui.bank = new Bank();
//        int option = 0;
//        for (int i = 0; i < 50; i++) {
//            System.out.print("=");
//            if (i == 24) System.out.print("  Welcome to " + gui.bank.name + "  ");
//        }
//        while (option != -1) {
//            System.out.print("""
//
//
//
//                    Services You can Use: \
//
//                    \t\t1) Add Client\
//
//                    \t\t2) Add Account of existing Client\
//
//                    \t\t3) Search a Client\
//
//                    \t\t4) Remove Client\
//
//                    \t\t5) Search Account\
//
//                    \t\t6) Check total amount of all amount\
//
//                    \t\t7) Withdraw Money\
//
//                    \t\t8) Deposit Money\
//
//                    Enter Your option=\s""");
//            Scanner opt = new Scanner(System.in);
//            try {
//                option = opt.nextInt();
//            } catch (RuntimeException e) {
//                System.out.println("Invalid Option.");
//            }
//
//            switch (option) {
//                case 1:
//                    Scanner phone = new Scanner(System.in);
//                    Scanner cnicEnter = new Scanner(System.in);
//                    System.out.print("Enter Name= ");
//                    p1.setName(opt.next().toUpperCase());
//                    System.out.print("Enter CNIC= ");
//                    p1.setCNIC(cnicEnter.next());
//                    System.out.print("Enter Phone no. = ");
//                    p1.setPhoneNo(phone.next());
//                    bank.addClient(p1);
//                    System.out.println("Client added Successfully.");
//                    break;
//
//
//                case 2:
//                    boolean found = false;
//                    int index = 0;
//                    System.out.print("Enter the ID of client= ");
//                    String id = opt.next();
//                    float amount;
//                    for (int i = 0; i < bank.getClList().length; i++) {
//                        if (id.equals(bank.getClList()[i].getId())) {
//                            index = i;
//                            found = true;
//                            break;
//                        }
//                    }
//                    if (found) {
//                        System.out.print("Enter the amount you want to add= ");
//                        amount = opt.nextFloat();
//                        bank.addAccount(id, amount, bank.getClList()[index]);
//                        if (bank.getClList()[index].getAccountCreated())
//                            System.out.println("Account add Successfully.");
//                        else System.out.println("Account containing limit is already fulled.");
//                    } else System.out.println("Client not found.");
//                    break;
//
//
//                case 3:
//                    System.out.print("Enter CNIC of Client = ");
//                    String cnic = opt.next();
//                    Client c = bank.searchCustomerDetail(cnic);
//                    if (c != null) System.out.println(c);
//                    else System.out.println("Client not found.");
//                    break;
//
//
//                case 4:
//                    System.out.print("Enter the Id of Client= ");
//                    String ID = opt.next();
//                    if (bank.removeClient(ID)) System.out.println("Client removed Successfully.");
//                    else System.out.println("Client not found.");
//                    break;
//
//
//                case 5:
//                    System.out.print("Enter the Id of Account= ");
//                    String Id = opt.next();
//                    Account a = bank.searchAccount(Id);
//                    if (a == null) System.out.println("Account not found.");
//                    else System.out.println(a);
//                    break;
//                case 6:
//                    System.out.println("Total amount of all accounts is " + bank.totalAmount());
//                    break;
//                case 7:
//                    System.out.print("Enter the Account Number= ");
//                    String num = opt.next();
//                    Client C = null;
//                    boolean Found = false;
//                    for (int i = 0; i < bank.getClientIndex(); i++) {
//                        for (Account A : bank.getClList()[i].getAcList()) {
//                            if (A != null && A.getNumber().equals(num)) {
//                                C = bank.getClList()[i];
//                                Found = true;
//                                break;
//                            }
//                        }
//                        if (Found) break;
//                    }
//                    if (!Found) System.out.println("Account not found.");
//                    else {
//                        System.out.print("Enter the amount you want to withdraw= ");
//                        float Amount = opt.nextFloat();
//                        C.withdraw(Amount, num);
//                    }
//                    break;
//
//
//                case 8:
//                    System.out.print("Enter the Account Number= ");
//                    String Num = opt.next();
//                    Client Cl = null;
//                    boolean FOUND = false;
//                    for (int i = 0; i < bank.getClientIndex(); i++) {
//                        for (Account A : bank.getClList()[i].getAcList()) {
//                            if (A != null && A.getNumber().equals(Num)) {
//                                Cl = bank.getClList()[i];
//                                FOUND = true;
//                                break;
//                            }
//                        }
//                        if (FOUND) break;
//                    }
//                    if (!FOUND) System.out.println("Account not found.");
//                    else {
//                        System.out.print("Enter the amount you want to withdraw= ");
//                        float Amount = opt.nextFloat();
//                        Cl.deposit(Amount, Num);
//                    }
//                    break;
//
//                case -1:
//                    bank.ClientAndAccount();
//                    break;
//                default:
//                    System.out.println("Invalid option");
//                    break;
//            }
//
//        }

        JFrame bankFrame = new JFrame(gui.bank.name);
        bankFrame.setSize(360,500);
        bankFrame.setLocationRelativeTo(null);
        bankFrame.setResizable(false);
        bankFrame.setLayout(null);

//        ImageIcon withdrawIcon      = new ImageIcon(BankManagementSystem.class.getResource("/resources/buttonIcon/withdraw.png"));
//        ImageIcon depositIcon       = new ImageIcon(BankManagementSystem.class.getResource("/resources/buttonIcon/deposit.png"));
//        ImageIcon removeClientIcon  = new ImageIcon(BankManagementSystem.class.getResource("/resources/buttonIcon/removeClient.png"));
//        ImageIcon searchAccountIcon = new ImageIcon(BankManagementSystem.class.getResource("/resources/buttonIcon/searchAccount.png"));
//        ImageIcon searchClientIcon  = new ImageIcon(BankManagementSystem.class.getResource("/resources/buttonIcon/searchClient.png"));
//        ImageIcon totalAmountIcon   = new ImageIcon(BankManagementSystem.class.getResource("/resources/buttonIcon/totalAmount.png"));
//        ImageIcon addClientIcon     = new ImageIcon(BankManagementSystem.class.getResource("/resources/buttonIcon/addClient.png"));
//        ImageIcon addAccountIcon    = new ImageIcon(BankManagementSystem.class.getResource("/resources/buttonIcon/addAccount.png"));
//        ImageIcon addClientIcon     = new ImageIcon("addClient.png");


        menuPanel = new JPanel();
        depositPanel = new JPanel();
        withdrawPanel = new JPanel();
        searchClientPanel = new JPanel();
        searchAccountPanel = new JPanel();
        removeClientPanel = new JPanel();
        addClientPanel = new JPanel();
        addAccountPanel = new JPanel();
        showAccountPanel = new JPanel();
        showClientPanel = new JPanel();

//        Buttons setup
        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        searchAccountButton = new JButton("Search Account");
        searchClientButton = new JButton("Search Client");
        removeClientButton = new JButton("Remove Client");
        totalAmountButton = new JButton("Total Amount");
        addClientButton = new JButton("Add Client");
        addAccountButton = new JButton("Add Account");

        withdrawButton.setFocusPainted(false);
        depositButton.setFocusPainted(false);
        totalAmountButton.setFocusPainted(false);
        searchAccountButton.setFocusPainted(false);
        searchClientButton.setFocusPainted(false);
        addAccountButton.setFocusPainted(false);
        addClientButton.setFocusPainted(false);
        removeClientButton.setFocusPainted(false);

        withdrawButton.addActionListener(gui);
        depositButton.addActionListener(gui);
        totalAmountButton.addActionListener(gui);
        removeClientButton.addActionListener(gui);
        searchClientButton.addActionListener(gui);
        searchAccountButton.addActionListener(gui);
        addClientButton.addActionListener(gui);
        addAccountButton.addActionListener(gui);

        addClientButton.setBounds(24,50,139,50);
        addAccountButton.setBounds(187,50,139,50);
        withdrawButton.setBounds(24,150,139,50);
        depositButton.setBounds(187,150,139,50);
        totalAmountButton.setBounds(24,250,139,50);
        removeClientButton.setBounds(187,250,139,50);
        searchClientButton.setBounds(24,350,139,50);
        searchAccountButton.setBounds(187,350,139,50);


//        Panel Add Client
        name = new JLabel("Name: ");
        name.setBounds(30,50,200,20);
        cnicLabel = new JLabel("CNIC: ");
        cnicLabel.setBounds(30,105,200,20);
        phoneLabel = new JLabel("Phone No.");
        phoneLabel.setBounds(30,160,200,20);
        username = new JTextField();
        username.setBounds(30, 75,300, 20);
        cnicField = new JTextField();
        cnicField.setBounds(30,130,300,20);
        phoneField = new JTextField();
        phoneField.setBounds(30,185,300,20);
        goAddClient = new JButton("Add Client.");
        goAddClient.setBounds(130, 220, 100, 50);
        goAddClient.setFocusPainted(false);
        goAddClient.addActionListener(gui);

//        Panel Add Account
        clientID = new JLabel("Client ID: ");
        clientID.setBounds(30,105,200,20);
        clientIdField = new JTextField();
        clientIdField.setBounds(27,130,300,20);
        addAccountAmountLabel = new JLabel("Amount");
        addAccountAmountLabel.setBounds(30,170,200,20);
        addAccountAmountField = new JTextField();
        addAccountAmountField.setBounds(27, 200, 300, 20);
        goAddAccount = new JButton("Add Account");
        goAddAccount.setBounds(105, 240, 150, 50);
        goAddAccount.addActionListener(gui);
        goAddAccount.setFocusPainted(false);

//        Panel Withdraw
        accountWithdrawLable = new JLabel("Account No.");
        accountWithdrawLable.setBounds(30,105,200,20);
        accountWithdrawField = new JTextField();
        accountWithdrawField.setBounds(27,130,300,20);
        amountWithdrawLabel = new JLabel("Amount");
        amountWithdrawLabel.setBounds(30,160,200,20);
        amountWithdrawField = new JTextField();
        amountWithdrawField.setBounds(30,185,300,20);
        goWithdraw = new JButton("Withdraw");
        goWithdraw.setBounds(130, 220, 100, 50);
        goWithdraw.setFocusPainted(false);
        goWithdraw.addActionListener(gui);

//        Panel Deposit
        accountDepositLable = new JLabel("Account No.");
        accountDepositLable.setBounds(30,105,200,20);
        accountDepositField = new JTextField();
        accountDepositField.setBounds(27,130,300,20);
        amountDepositLabel = new JLabel("Amount");
        amountDepositLabel.setBounds(30,160,200,20);
        amountDepositField = new JTextField();
        amountDepositField.setBounds(30,185,300,20);
        goDeposit = new JButton("Deposit");
        goDeposit.setBounds(130, 220, 100, 50);
        goDeposit.setFocusPainted(false);
        goDeposit.addActionListener(gui);

//        Panel Remove Client
        removeClientIdLabel = new JLabel("Client ID: ");
        removeClientIdLabel.setBounds(30,105,200,20);
        removeClientIdField = new JTextField();
        removeClientIdField.setBounds(27,130,300,20);
        goRemoveClient = new JButton("Remove Client");
        goRemoveClient.setBounds(105, 220, 150, 50);
        goRemoveClient.addActionListener(gui);
        goRemoveClient.setFocusPainted(false);

//        Panel Search Client
        searchClientIDLabel = new JLabel("Client CNIC: ");
        searchClientIDLabel.setBounds(30,105,200,20);
        searchClientIDField = new JTextField();
        searchClientIDField.setBounds(27,130,300,20);
        goSearchClient = new JButton("Search Client");
        goSearchClient.setBounds(105, 220, 150, 50);
        goSearchClient.addActionListener(gui);
        goSearchClient.setFocusPainted(false);

//        Panel Search Account
        searchAccountIDLabel = new JLabel("Account No: ");
        searchAccountIDLabel.setBounds(30,105,200,20);
        searchAccountIDField = new JTextField();
        searchAccountIDField.setBounds(27,130,300,20);
        goSearchAccount = new JButton("Search Account");
        goSearchAccount.setBounds(105, 220, 150, 50);
        goSearchAccount.addActionListener(gui);
        goSearchAccount.setFocusPainted(false);











        addClientPanel.add(name);
        addClientPanel.add(username);
        addClientPanel.add(cnicLabel);
        addClientPanel.add(phoneField);
        addClientPanel.add(phoneLabel);
        addClientPanel.add(cnicField);
        addClientPanel.add(goAddClient);

        addAccountPanel.add(clientID);
        addAccountPanel.add(clientIdField);
        addAccountPanel.add(addAccountAmountField);
        addAccountPanel.add(addAccountAmountLabel);
        addAccountPanel.add(goAddAccount);

        withdrawPanel.add(accountWithdrawField);
        withdrawPanel.add(accountWithdrawLable);
        withdrawPanel.add(goWithdraw);
        withdrawPanel.add(amountWithdrawField);
        withdrawPanel.add(amountWithdrawLabel);

        depositPanel.add(accountDepositField);
        depositPanel.add(accountDepositLable);
        depositPanel.add(goDeposit);
        depositPanel.add(amountDepositField);
        depositPanel.add(amountDepositLabel);

        removeClientPanel.add(removeClientIdLabel);
        removeClientPanel.add(removeClientIdField);
        removeClientPanel.add(goRemoveClient);

        searchClientPanel.add(searchClientIDLabel);
        searchClientPanel.add(searchClientIDField);
        searchClientPanel.add(goSearchClient);

        searchAccountPanel.add(searchAccountIDLabel);
        searchAccountPanel.add(searchAccountIDField);
        searchAccountPanel.add(goSearchAccount);




        //Panels setup
        menuPanel.setBounds(0,0,360,450);
        withdrawPanel.setBounds(0,0,360,450);
        depositPanel.setBounds(0,0,360,450);
        addAccountPanel.setBounds(0,0,360,450);
        addClientPanel.setBounds(0,0,360,450);
        removeClientPanel.setBounds(0,0,360,450);
        searchAccountPanel.setBounds(0,0,360,450);
        searchClientPanel.setBounds(0,0,360,450);
        showClientPanel.setBounds(65,290,200,50);
        showAccountPanel.setBounds(65,290,200,50);

        menuPanel.add(withdrawButton);
        menuPanel.add(depositButton);
        menuPanel.add(addAccountButton);
        menuPanel.add(addClientButton);
        menuPanel.add(searchAccountButton);
        menuPanel.add(searchClientButton);
        menuPanel.add(totalAmountButton);
        menuPanel.add(removeClientButton);

        menuPanel.setLayout(null);
        withdrawPanel.setLayout(null);
        depositPanel.setLayout(null);
        searchAccountPanel.setLayout(null);
        searchClientPanel.setLayout(null);
        addAccountPanel.setLayout(null);
        addClientPanel.setLayout(null);
        removeClientPanel.setLayout(null);
        showClientPanel.setLayout(null);
        showAccountPanel.setLayout(null);



        menuPanel.setVisible(true);
        depositPanel.setVisible(false);
        withdrawPanel.setVisible(false);
        searchClientPanel.setVisible(false);
        searchAccountPanel.setVisible(false);
        removeClientPanel.setVisible(false);
        addAccountPanel.setVisible(false);
        addClientPanel.setVisible(false);
        showClientPanel.setVisible(false);
        showAccountPanel.setVisible(false);

        bankFrame.add(menuPanel);
        bankFrame.add(depositPanel);
        bankFrame.add(searchAccountPanel);
        bankFrame.add(searchClientPanel);
        bankFrame.add(removeClientPanel);
        bankFrame.add(withdrawPanel);
        bankFrame.add(addAccountPanel);
        bankFrame.add(addClientPanel);
        addAccountPanel.add(showAccountPanel);
        addClientPanel.add(showClientPanel);

        bankFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bankFrame.addWindowListener(new java.awt.event.WindowAdapter(){public void windowClosing(java.awt.event.WindowEvent e){gui.bank.ClientAndAccount();}});
        bankFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        menuPanel.setVisible(false);
        c = null;
        if(e.getSource() == addClientButton){
            addClientPanel.setVisible(true);
        }

        else if(e.getSource() == addAccountButton){
            addAccountPanel.setVisible(true);
        }

        else if(e.getSource() == removeClientButton){
            removeClientPanel.setVisible(true);
        }

        else if(e.getSource() == totalAmountButton){
            JOptionPane.showMessageDialog(null,"Total amount of all accounts is " + bank.totalAmount());
            menuPanel.setVisible(true);
        }

        else if(e.getSource() == withdrawButton){
            withdrawPanel.setVisible(true);
        }

        else if(e.getSource() == depositButton){
            depositPanel.setVisible(true);
        }

        else if(e.getSource() == searchAccountButton){
            searchAccountPanel.setVisible(true);
        }

        else if(e.getSource() == searchClientButton){
            searchClientPanel.setVisible(true);
        }

        else if(e.getSource() == goAddClient){
            Person p1 = new Person();
            if(username.getText().isEmpty()) JOptionPane.showMessageDialog(null,"Name Field is empty.","Error",JOptionPane.ERROR_MESSAGE);
            else if(cnicField.getText().isEmpty()) JOptionPane.showMessageDialog(null,"CNIC Field is empty.","Error",JOptionPane.ERROR_MESSAGE);
            else if(phoneField.getText().isEmpty()) JOptionPane.showMessageDialog(null,"Phone no. Field is empty.","Error",JOptionPane.ERROR_MESSAGE);
            if(checkCnic(cnicField,phoneField)){
                p1.setName(username.getText());
                p1.setCNIC(cnicField.getText());
                p1.setPhoneNo(phoneField.getText());
                bank.addClient(p1);
            }
            addClientPanel.setVisible(false);
            menuPanel.setVisible(true);
            username.setText("");
            phoneField.setText("");
            cnicField.setText("");
        }

        else if(e.getSource() == goAddAccount){
            if(clientIdField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Client Id field is empty.","Error", JOptionPane.ERROR_MESSAGE);
            else if(addAccountAmountField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Amount field is empty.","Error", JOptionPane.ERROR_MESSAGE);

            if(foundClient(clientIdField) && amountCheck(addAccountAmountField)){
                Account a = bank.addAccount(clientIdField.getText(),Float.parseFloat(addAccountAmountField.getText()),this.c);
                if(a != null) JOptionPane.showMessageDialog(null, "Account added successfully.","Info", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(null, "Account Some error occured.","Error", JOptionPane.ERROR_MESSAGE);
            }
            addAccountPanel.setVisible(false);
            menuPanel.setVisible(true);
            username.setText("");
            phoneField.setText("");
            cnicField.setText("");
        }

        else if(e.getSource() == goWithdraw){
            if(accountWithdrawField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Account Id field is empty.","Error", JOptionPane.ERROR_MESSAGE);
            else if(amountWithdrawField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Amount field is empty.","Error", JOptionPane.ERROR_MESSAGE);
            else if(foundAccount(accountWithdrawField) && amountCheck(amountWithdrawField)){
                c.withdraw(Float.parseFloat(amountWithdrawField.getText()),accountWithdrawField.getText());
            }
            accountWithdrawField.setText("");
            amountWithdrawField.setText("");
            withdrawPanel.setVisible(false);
            menuPanel.setVisible(true);
        }

        else if(e.getSource() == goDeposit){
            c = null;
            if(accountDepositField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Account Id field is empty.","Error", JOptionPane.ERROR_MESSAGE);
            else if(amountDepositField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Amount field is empty.","Error", JOptionPane.ERROR_MESSAGE);
            else if(foundAccount(accountDepositField) && amountCheck(amountDepositField)){
                c.deposit(Float.parseFloat(amountDepositField.getText()),accountDepositField.getText());
            }
            accountDepositField.setText("");
            amountDepositField.setText("");
            depositPanel.setVisible(false);
            menuPanel.setVisible(true);
        }

        else if(e.getSource() == goRemoveClient){
            if(removeClientIdField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Client Id field is empty.","Error", JOptionPane.ERROR_MESSAGE);
            else if(foundClient(removeClientIdField)){
                if(bank.removeClient(removeClientIdField.getText())) JOptionPane.showMessageDialog(null, "Client removed successfully.","Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else  JOptionPane.showMessageDialog(null, "Client not found.","Error", JOptionPane.ERROR_MESSAGE);
            removeClientIdField.setText("");
            removeClientPanel.setVisible(false);
            menuPanel.setVisible(true);
        }

        else if(e.getSource() == goSearchClient){
            if(searchClientIDField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "CNIC field is empty.","Error", JOptionPane.ERROR_MESSAGE);
            else if(!searchClientIDField.getText().isEmpty()){
                try{
                    Double d = Double.parseDouble(searchClientIDField.getText());
                    if(searchClientIDField.getText().length() != 13) {
                        JOptionPane.showMessageDialog(null, "CNIC must be of size 13.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, "CNIC must be numeric.","Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            Client cl = bank.searchCustomerDetail(searchClientIDField.getText());
            if(cl!=null){
                String accountDetail = "";
                int i = 1;
                for(Account a: cl.getAcList()){
                    if(a != null){
                        accountDetail += "Account ID "+i+":\t"+a.getNumber()+"\nAmount:\t"+a.getAmount()+"\n";
                        i++;
                    }
                }
                JOptionPane.showMessageDialog(null,"Client ID: "+cl.getId()+"\nName: "+
                        cl.getPersonDetails().getName()+"\nCNIC: "+
                        cl.getPersonDetails().getCNIC()+"\nPhone NO: "+
                        cl.getPersonDetails().getPhoneNo()+"\n"+
                        accountDetail,"Info",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Client Not Found.","Error", JOptionPane.ERROR_MESSAGE);
            }
            searchClientIDField.setText("");
            searchClientPanel.setVisible(false);
            menuPanel.setVisible(true);
        }

        else if(e.getSource() == goSearchAccount){
            Account a = null;
            if(searchAccountIDField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "CNIC field is empty.","Error", JOptionPane.ERROR_MESSAGE);
            else a = bank.searchAccount(searchAccountIDField.getText());
            if(a != null){
                c = a.getACholder();
                String accountDetail = "";
                int i = 1;
                for(Account A: c.getAcList()){
                    if(A != null){
                        accountDetail += "Account ID "+i+":\t"+A.getNumber()+"\nAmount:\t"+A.getAmount()+"\n";
                        i++;
                    }
                }
                JOptionPane.showMessageDialog(null,"Client ID: "+c.getId()+"\nName: "+c.getPersonDetails().getName()+"\nCNIC: "+c.getPersonDetails().getCNIC()+"\nPhone NO: "+c.getPersonDetails().getPhoneNo()+"\n"+accountDetail,"Info",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Account Not Found.","Error", JOptionPane.ERROR_MESSAGE);
            }
            searchAccountIDField.setText("");
            searchAccountPanel.setVisible(false);
            menuPanel.setVisible(true);
        }

    }

    boolean checkCnic(JTextField cnic, JTextField phone){
        if(phone == null) return cnicCheck(cnic);
        else return cnicCheck(cnic) && phoneCheck(phone);
    }

    boolean amountCheck(JTextField amount){
        try{
            Double p = Double.parseDouble(amount.getText());
            if(p>0) return true;
            else JOptionPane.showMessageDialog(null,"Amount must be greater than 0.","Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,"Amount must contain numeric value.","Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    boolean foundClient(JTextField a){
        for(Client C: bank.getClList()){
            if(C != null && C.getId().equals(a.getText())) {
                this.c = C;
                return true;
            }
        }
        return false;
    }

    boolean foundAccount(JTextField a){
        for(Account A: bank.getAcList()){
            if(A != null && A.getNumber().equals(a.getText())){
                this.c = A.getACholder();
                return true;
            }
        }
        return false;
    }

    boolean cnicCheck(JTextField j){
        try{
            Double c = Double.parseDouble(j.getText());
            if(j.getText().length() != 13) JOptionPane.showMessageDialog(null,"CNIC must be of size 13.","Error", JOptionPane.ERROR_MESSAGE);
            else {
                if(foundClient(j)) JOptionPane.showMessageDialog(null,"Client is already created on this cnic..","Error", JOptionPane.ERROR_MESSAGE);
                return !foundClient(j);
            }
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null,"CNIC must contain numeric value.","Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    boolean phoneCheck(JTextField phone){
        try{
            Double p = Double.parseDouble(phone.getText());
            if(phone.getText().length() == 11) return true;
            else JOptionPane.showMessageDialog(null,"Phone No. must be of size 11.","Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,"Phone No. must contain numeric value.","Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}