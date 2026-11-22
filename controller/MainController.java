package controller;

import view.MainView;

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
                    //SaleController.main(args);
                    break;
                case 5:
                    //ReportController.main(args);
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