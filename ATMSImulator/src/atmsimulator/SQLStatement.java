/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulator;

/**
 *
 * @author Vasilis
 */
public class SQLStatement {
    
    public static String insertRecord(String tableName, int id, int balance){
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(tableName);
        builder.append(" VALUES ");
        builder.append("(");
        builder.append(id);
        builder.append(", ");
        builder.append(balance);
        builder.append(")");
        
        return builder.toString();
    }

    public static String updateRecord(String tableName, int id, int newBalance){
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ");
        builder.append(tableName);
        builder.append(" \n");
        builder.append("SET ");
        builder.append("balance = ");
        builder.append(newBalance);
        builder.append("\n");
        builder.append("WHERE id = ");
        builder.append(id);
        builder.append(";");
        
        return builder.toString();
    }
    
    public static String selectRecord(String tableName, int id){
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT balance from ");
        builder.append(tableName + "\n");
        builder.append("WHERE id = ");
        builder.append(id);
        
        return builder.toString();
    }
}