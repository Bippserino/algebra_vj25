import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        DataSource dataSource = createDataSource();

        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Uspješno spojeni na bazu podataka!");

            Scanner sc = new Scanner(System.in);
            String sql = "INSERT INTO dbo.drzava (naziv) VALUES (?);";
            ArrayList<String> drzave = new ArrayList<>(Arrays.asList("Francuska", "Japan", "Grčka", "SAD", "UK", "Belgija", "Nizozemska", "BiH", "Kanada", "Brazil"));

            for(var d : drzave) {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, d);

                int rowsAffected = ps.executeUpdate();
                System.out.println("Država je uspješno dodana!");
            }

            sql = "SELECT idDrzava FROM dbo.drzava WHERE naziv = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, drzave.get(0));

            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("idDrzava");

            System.out.println("Brisanje država");

            CallableStatement cs = connection.prepareCall("{call ObrisiDrzaveOdId(?)}");
            cs.setInt(1, id);
            cs.execute();
            System.out.println("Uspješno obrisane.");

        } catch (SQLException e) {
            System.err.println("Greška prilikom spajanja na bazu podataka:");
            e.printStackTrace();
        }
    }

    // Metoda za stvaranje DataSource objekta
    private static DataSource createDataSource() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setEncrypt(false);
        dataSource.setServerName("BOJAN\\SQLEXPRESS");
        dataSource.setPortNumber(15234);
        dataSource.setDatabaseName("AdventureWorksOBP");
        dataSource.setUser("sa");
        dataSource.setPassword("SQL");

        return dataSource;
    }
}