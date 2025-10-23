package portfolio_manager;
import java.util.*;
public class predict {
    private Vector<Double>stock_prices=new Vector<>();
    private Vector<Double>Time=new Vector<>();
    private Vector<Double>model=new Vector<>();
    public void read(Stock s){
        Iterator<Double> traverse=s.get_Iterator();
        Iterator<Double> traverse_time=s.get_time_iterator();
        while(traverse.hasNext()){
            stock_prices.add(traverse.next());
        }
        while(traverse_time.hasNext()){
            Time.add(traverse_time.next());
        }
    }

    private double[][] construct_input_matrix(int x){
        double[][] matrix=new double[x][2];
        for(int i=0;i<x;i++){
            matrix[i][0]=Time.get(i);
            matrix[i][1]=1;
        }
        return matrix;
    }
    private double[][] multiply_matrices(double[][] matrix1,int row1,int column1, double[][]matrix2 ,int row2,int column2 ){
        if(column1==row2){
            double[][] mul=new double[row1][column2]; 
            for(int i=0;i<row1;i++){
                for(int j=0;j<column2;j++){
                    double sum=0;
                    for(int k=0;k<row2;k++){
                        sum+=(matrix1[i][k])*(matrix2[k][j]);
                    }
                    mul[i][j]=sum;
                }
            }
            return mul;
        }else{
            double[][] mul=new double[0][0];
            System.out.println("enter multipliable matrices");
            return mul;
        }
    }
    private double[][] construct_output_matrix(int x){
        double[][] mat=new double[x][1];
        for(int i=0;i<x;i++){
            mat[i][0]=stock_prices.get(i);
        }
        return mat;
    }
    private double[][] transpose(double[][] mat,int row,int column){
        double[][] trans=new double[column][row];
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                trans[j][i]=mat[i][j];
            }
        }
        return trans;
    }
    private double[][] inverse(double[][] matrix,int row){
        double[][] inv=new double[row][row];
        double[][] aug=new double[row][2*row];
        for(int i=0;i<row;i++){
            for(int j=0;j<row;j++){
                aug[i][j]=matrix[i][j];
            }
        }
        for(int i=0;i<row;i++){
            for(int j=row;j<2*row;j++){
                if(i==j-row){
                    aug[i][j]=1;
                }else{
                    aug[i][j]=0;
                }
            }
        }
        int ch=0;
        for(int i=0;i<row;i++){
            for(int j=i;j<row;j++){
                if(j==i)continue;
                if(matrix[i][i]==0){
                    ch=1;
                    break;
                }
                double factor=(aug[j][i])/(aug[i][i]);
                for(int k=0;k<2*row;k++){
                    aug[j][k]-=factor*(aug[i][k]);
                }
            }
            if(ch==1)break;
        }
        if(ch==1){
            throw new RuntimeException("one or more events are dependent");
        }
        for(int i=row-1;i>=0;i--){
            for(int j=i;j>=0;j--){
                if(j==i)continue;
                double factor=(aug[j][i])/(aug[i][i]);
                for(int k=0;k<2*row;k++){
                    aug[j][k]-=factor*(aug[i][k]);
                }
            }
        }
        for(int i=0;i<row;i++){
            for(int j=0;j<2*row;j++){
                aug[i][j]=(aug[i][j])/(aug[i][i]);
            }
        }
        for(int i=0;i<row;i++){
            for(int j=row;j<2*row;j++){
                inv[i][j-row]=aug[i][j];
            }
        }

        return inv;
    }
    public void trainmodel(Stock s){
        read(s);
        double [][] input_matrix=construct_input_matrix(Time.size());
        double[][] output_matrix=construct_output_matrix(Time.size());
        double[][] mul=multiply_matrices(transpose(input_matrix, Time.size(), 2), 2, Time.size(), input_matrix, Time.size(), 2);
        double[][] inv=inverse(mul, 2);
        double[][] transpose_input_matrix = transpose(input_matrix, Time.size(), 2);
        double[][] parameters=multiply_matrices(inv, 2, 2, transpose_input_matrix, 2, Time.size());
        parameters=multiply_matrices(parameters, 2, Time.size(), output_matrix, Time.size(), 1);
        Vector<Double>v=new Vector<>();
        for(int i=0;i<2;i++){
            model.add(parameters[i][0]);
        }
        return ;
    }
    public double predict(Stock s,double time){
        return (model.get(0))*time+(model.get(1));
    }

}
