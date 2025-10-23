package portfolio_manager;
import java.util.*;

public class portfolio{
    private double amount=0;
    private Vector<Stock> holdings=new Vector<>(0);
    private String password;
    private boolean verifyPassword(String passKey, Scanner sc){
    if(this.password.equals(passKey)) return true;

    while(true){
        System.out.println("Incorrect password:");
        System.out.println("1. Quit");
        System.out.println("2. Enter password again");
        int choice = sc.nextInt();
        sc.nextLine(); 
        if(choice == 1) return false;
        if(choice == 2){
            System.out.print("Enter password: ");
            passKey = sc.nextLine();
            if(this.password.equals(passKey)) return true;
        }
    }
}

    public portfolio(String passKey,double amount){
        this.password=passKey;
        this.amount=amount;
    }
    public Iterator<Stock> get_holdingsIterator(){
        return holdings.iterator();
    }
    public void display_all_stocks(){
        if(holdings.isEmpty()){
            System.out.println("you have no stocks!");
            return;
        }else{
            for(Stock s:holdings){
                s.show();
            }
        }

    }
    public void display_stocks_of_certain_company(String company){
        int count=0;
        for(Stock s:holdings){
            if(s.get_company_name().equals(company)){
                s.show();
                count++;
            }
        }
        if(count==0){
            System.out.println("you dont have any stocks of this company");
        }

    }
    public void display_all_stocks_with_highest_price_per_share(){
        double highest_price=-Double.MAX_VALUE;
        for(Stock s: holdings){
            if(highest_price<s.get_price_per_share()){
                highest_price=s.get_price_per_share();
            }
        }
        for( Stock s: holdings){
            if(s.get_price_per_share()==highest_price){
                s.show();
            }
        }
    }
    public void display_all_stocks_with_lowest_price_per_share(){
        double lowest_price=Double.MAX_VALUE;
        for(Stock s: holdings){
            if(lowest_price>s.get_price_per_share()){
                lowest_price=s.get_price_per_share();
            }
        }
        for( Stock s: holdings){
            if(s.get_price_per_share()==lowest_price){
                s.show();
            }
        }
    }
    public void display_stocks_currently_in_loss(){
        for(Stock s: holdings){
            int count=0;
            double update=-Double.MAX_VALUE;
            Iterator<Double> traverse=s.get_Iterator();
            while(traverse.hasNext()){
                double x=traverse.next();
                if(update<x){
                    update=x;
                    count=0;
                }else{
                    count++;
                }
            }
            if(count>0){
                s.show();
                System.out.println("--stock is in loss from last "+count+" updates--");
            }
        }
    }
    public void display_stocks_currently_in_profit(){
        for(Stock s: holdings){
            int count=0;
            double update=-Double.MAX_VALUE;
            Iterator<Double> traverse=s.get_Iterator();
            while(traverse.hasNext()){
                double x=traverse.next();
                if(update<x){
                    update=x;
                    count++;
                }else{
                    count=0;
                }
            }
            if(count>0){
                s.show();
                System.out.println("--stock is in profit from last "+count+" updates--");
            }
        }
    }
    public double get_amount(){
        return amount;
    }
    public void display_amount(){
        System.out.println("Amount:"+amount);
    }
    public void add_amount(double add){
        if(add>0){
            amount+=add;
            return;
        }else{
            System.out.println("enter positive amount");
        }
    }
    public void remove_amount(double remove,String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
        if(remove>amount){
            System.out.println("you dont have much amount!");
        }else{
            amount-=remove;
            System.out.println("amount removed successfully!");
        }
    }
    public void buy_stock(String symbol, String companyName, int quantity, double priceperShare,int stock_id,String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
        if(priceperShare*quantity>amount){
            System.out.println("can't buy this stock as there is not enough money available");
            return ;
        }
        amount-=priceperShare*quantity;
        holdings.add(new Stock(symbol, companyName, quantity, priceperShare, stock_id));
        System.out.println("stock brought successfully");
        return;
    }
    public void update_stock(int stockid,double new_price,Scanner sc){
        for(Stock s:holdings){
            if(s.get_stock_id()==stockid){
                s.updatePrice(new_price,sc);
            }
        }
    }
    public void sell_all_stocks(String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
        for(Stock s:holdings){
            amount+=s.get_value();
        }
        holdings.removeAllElements();
        System.out.println("new Amount"+amount);
        System.out.println("all stocks sold");
        return;
    }
    public void sell_stocks_of_certain_company(String company,String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
        Iterator<Stock> traverese=holdings.iterator();
        while(traverese.hasNext()){
            Stock s=traverese.next();
            if(s.get_company_name().equals(company)){
                amount+=s.get_value();
                traverese.remove();
            }
        }

    }
    public void sell_certain_stock(int stockid,String passKey,Scanner sc){
        if(verifyPassword(passKey, sc)==false)return;
                            Iterator<Stock> traverese=holdings.iterator();
        while(traverese.hasNext()){
            Stock s=traverese.next();
            if(s.get_stock_id()==stockid){
                amount+=s.get_value();
                traverese.remove();
                break;
            }
        }
    }

}
