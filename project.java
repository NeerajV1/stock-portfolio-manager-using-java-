import java.util.*;
import portfolio_manager.*;
public class project{
        public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Portfolio Manager!");

        
        System.out.print("Set your portfolio password: ");
        String password = sc.nextLine();

        System.out.print("Enter initial amount: ");
        double initialAmount = sc.nextDouble();
        sc.nextLine(); 

        portfolio_manager.portfolio myPortfolio = new portfolio_manager.portfolio(password, initialAmount);
        portfolio_manager.predict predictors=new portfolio_manager.predict();

        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Display all stocks");
            System.out.println("2. Display stocks of a company");
            System.out.println("3. Display stocks with highest price per share");
            System.out.println("4. Display stocks with lowest price per share");
            System.out.println("5. Display stocks in profit");
            System.out.println("6. Display stocks in loss");
            System.out.println("7. Display current amount");
            System.out.println("8. Add amount");
            System.out.println("9. Remove amount");
            System.out.println("10. Buy stock");
            System.out.println("11. Sell all stocks");
            System.out.println("12. Sell stocks of a company");
            System.out.println("13. Sell a certain stock");
            System.out.println("14. Update stock price");
            System.out.println("15.Predict stock prices");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    myPortfolio.display_all_stocks();
                    break;
                case 2:
                    System.out.print("Enter company name: ");
                    String company = sc.nextLine();
                    myPortfolio.display_stocks_of_certain_company(company);
                    break;
                case 3:
                    myPortfolio.display_all_stocks_with_highest_price_per_share();
                    break;
                case 4:
                    myPortfolio.display_all_stocks_with_lowest_price_per_share();
                    break;
                case 5:
                    myPortfolio.display_stocks_currently_in_profit();
                    break;
                case 6:
                    myPortfolio.display_stocks_currently_in_loss();
                    break;
                case 7:
                    myPortfolio.display_amount();
                    break;
                case 8:
                    System.out.print("Enter amount to add: ");
                    double addAmt = sc.nextDouble();
                    sc.nextLine();
                    myPortfolio.add_amount(addAmt);
                    break;
                case 9:
                    System.out.print("Enter amount to remove: ");
                    double removeAmt = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter password: ");
                    String passKeyRemove = sc.nextLine();
                    myPortfolio.remove_amount(removeAmt, passKeyRemove, sc);
                    break;
                case 10:
                    System.out.print("Enter stock symbol: ");
                    String symbol = sc.nextLine();
                    System.out.print("Enter company name: ");
                    String companyName = sc.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();
                    System.out.print("Enter price per share: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter stock id: ");
                    int stockId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter password: ");
                    String passKeyBuy = sc.nextLine();
                    myPortfolio.buy_stock(symbol, companyName, quantity, price, stockId, passKeyBuy, sc);
                    break;
                case 11:
                    System.out.print("Enter password: ");
                    String passKeySellAll = sc.nextLine();
                    myPortfolio.sell_all_stocks(passKeySellAll, sc);
                    break;
                case 12:
                    System.out.print("Enter company name: ");
                    String sellCompany = sc.nextLine();
                    System.out.print("Enter password: ");
                    String passKeySellCompany = sc.nextLine();
                    myPortfolio.sell_stocks_of_certain_company(sellCompany, passKeySellCompany, sc);
                    break;
                case 13:
                    System.out.print("Enter stock id: ");
                    int sellStockId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter password: ");
                    String passKeySellStock = sc.nextLine();
                    myPortfolio.sell_certain_stock(sellStockId, passKeySellStock, sc);
                    break;
                case 14:
                    System.out.print("Enter stock id to update: ");
                    int updateId = sc.nextInt();
                    System.out.print("Enter new price: ");
                    double newPrice = sc.nextDouble();
                    sc.nextLine();
                    myPortfolio.update_stock(updateId, newPrice,sc);
                    break;
                case 15:
                                    System.out.print("Enter stock id to predict: ");
                    int stockIdPredict = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter time value: ");
                    double time = sc.nextDouble();
                    sc.nextLine();
                    portfolio_manager.Stock stockToPredict = null;
                    Iterator<portfolio_manager.Stock>holdingsIterator=myPortfolio.get_holdingsIterator();
                    while(holdingsIterator.hasNext()){
                        portfolio_manager.Stock s=holdingsIterator.next();
                        if(s.get_stock_id()==stockIdPredict){
                            stockToPredict=s;
                            break;
                        }
                    }

                    if (stockToPredict != null) {
                        predictors.trainmodel(stockToPredict);
                        double predictedPrice = predictors.predictor(time);
                        System.out.println("Predicted stock price: " + predictedPrice);
                    } else {
                        System.out.println("Stock ID not found!");
                    }
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting Portfolio Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }

        sc.close();
    }
}