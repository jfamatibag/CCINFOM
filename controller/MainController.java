package controller;

import view.MainView;

//javac -cp ".;mysql-connector-j-26.10.0.jar" connection\*.java model\*.java view\*.java controller\*.java
//java -cp ".;mysql-connector-j-26.10.0.jar" controller.MainController

public class MainController {

    public static void main (String [] args){
        MainView mainView = new MainView();

        while (true) {
            switch(mainView.displayMainMenu()) {
                case 1:
                    BranchController.main(args);
                    break;
                case 2:
                    GameController.main(args);
                    break;
                case 3:
                    CustomerController.main(args);
                    break;
                case 4:
                    SalesController.main(args);
                    break;
                case 5:
                   ReportController.main(args);
                    break;
                case 6:
                    mainView.displayExit();
                    return;
                default:
                    mainView.displayInvalid();
            }
        }
    }
}