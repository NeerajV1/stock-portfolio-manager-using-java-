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
    private double[][] multiply_matrices(double[][] A, int r1, int c1,
                                     double[][] B, int r2, int c2) {
    if (c1 != r2) {
        throw new IllegalArgumentException("Matrix dimensions don't match for multiplication");
    }

    double[][] result = new double[r1][c2];
    for (int i = 0; i < r1; i++) {
        for (int j = 0; j < c2; j++) {
            double sum = 0;
            for (int k = 0; k < c1; k++) {   
                sum += A[i][k] * B[k][j];
            }
            result[i][j] = sum;
        }
    }
    return result;
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
                if(aug[i][i]==0){
                    int reach=-1;
                    for(int k=i+1;k<row;k++){
                        if(aug[k][i]!=0){
                            reach=k;
                        }
                    }
                    if(reach==-1){
                    ch=1;
                    break;
                    }
                    for(int k=0;k<2*row;k++){
                        double temp=aug[i][k];
                        aug[i][k]=aug[reach][k];
                        aug[reach][k]=temp;
                    }

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
            double x=aug[i][i];
            for(int j=0;j<2*row;j++){
                aug[i][j]=(aug[i][j])/x;
            }
        }
        for(int i=0;i<row;i++){
            for(int j=row;j<2*row;j++){
                inv[i][j-row]=aug[i][j];
            }
        }

        return inv;
    }
    public void trainmodel(Stock s) {
    read(s);
    int n = Time.size();
    double[][] X = construct_input_matrix(n);     
    double[][] Y = construct_output_matrix(n);    

    
    double[][] Xt = transpose(X, n, 2);           

    
    double[][] XtX = multiply_matrices(Xt, 2, n, X, n, 2);  

    
    double[][] XtX_inv = inverse(XtX, 2);         

    
    double[][] XtY = multiply_matrices(Xt, 2, n, Y, n, 1);  

    
    double[][] beta = multiply_matrices(XtX_inv, 2, 2, XtY, 2, 1); 

    
    model.clear();
    model.add(beta[0][0]);  
    model.add(beta[1][0]);  
}

    public double predictor(double time){
        return (model.get(0))*time+(model.get(1));
    }
}
