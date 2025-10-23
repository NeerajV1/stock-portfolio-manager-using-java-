package portfolio_manager;
import java.util.*;
public class Stock{
    private String symbol;
    private String companyName;
    private int quantity;
    private double priceperShare;
    private int stock_id;
    private Vector<Double> stock_updates=new Vector<>(0);
    private Vector<Double> input_time=new Vector<>();
    public Stock(String symbol, String companyName, int quantity, double priceperShare,int stock_id){
          this.symbol = symbol ;
          this.companyName = companyName;
          this.quantity = quantity;
          this.priceperShare = priceperShare;
          this.stock_id=stock_id;
    }
    public double get_value(){
        return quantity*priceperShare;
    }
    public Iterator<Double> get_Iterator(){
        return stock_updates.iterator();
    }
    public Iterator<Double> get_time_iterator(){
        return input_time.iterator();
    }
    public String get_symbol(){
        return symbol;
    }
    public int get_quantity(){
        return quantity;
    }
    public double get_price_per_share(){
        return this.priceperShare;
    }
    public String get_company_name(){
        return this.companyName;
    }
    public int get_stock_id(){
        return this.stock_id;
    }
    public void updatePrice(double newprice ,Scanner sc){
    if(stock_updates.isEmpty()){
            stock_updates.add(this.priceperShare);
            System.out.println("enter time of first time buying this(in days):");
            input_time.add(sc.nextDouble());
        }
        this.priceperShare = newprice;
        stock_updates.add(newprice);
        System.out.println("enter time at which stock changed(in days):");
        input_time.add(sc.nextDouble());
    }
    public void addquantity(int qty){
        if(qty<0){
            System.out.println("cant add negative quantity");
            return;
        }else if(qty==0){
            System.out.println("no quantity to add");
            return;
        }
        this.quantity +=qty;
        System.out.println("successfully added!");
    }
    public void remove_quantity(int qty){
        if(qty <= quantity) this.quantity -= qty;
        else System.out.println("not enough stock to remove !");
    }
    public void show(){
        System.out.println("------------");
        System.out.println("Stock id:"+this.stock_id);
        System.out.println("Symbol:"+this.symbol);
        System.out.println("Company Name:"+this.companyName);
        System.out.println("Quantity of stocks buyed: "+this.quantity);
        System.out.println("Price of stock per share:"+this.priceperShare);
    }
}
