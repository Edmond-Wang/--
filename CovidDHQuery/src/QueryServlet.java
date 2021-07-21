import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;


@WebServlet("/QueryServlet")
public class QueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String update = runPY();

        String continent = req.getParameter("continent"); //从req中获得前端的信息
        String country = req.getParameter("country");
        continent = continent.trim();
        country = country.trim();
        String total = "wrong", dead = "wrong", today = "wrong", level = "wrong", continentJ = continent;
        String inputError = "";

        //***************************************************************************************8

        try { //连接数据库
            String URL = "jdbc:mysql://localhost:3306/CovidDHQuery?" +
                         "serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
            String USER = "root";
            String PASSWORD = "Wx136611";
            String sqlStr = "select total, dead, today, continent from info where country=?";

            Class.forName("com.mysql.cj.jdbc.Driver"); //加载驱动
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pst = con.prepareStatement(sqlStr); //预处理用来在sql语句中添加变量

            pst.setString(1, country); //替代sql语句中的问号，实现动态sql语句
            Statement st = con.createStatement(); //用来执行sql语句
            ResultSet rs = pst.executeQuery();

            while(rs.next()){ //从数据库中获得数据
                total = rs.getString("total");
                dead = rs.getString("dead");
                today = rs.getString("today");
                level = judge(today);
                continentJ = rs.getString("continent");
            }

            if(!Objects.equals(continent, continentJ) || Objects.equals(total, "wrong")){
                inputError = "输入有误！请检查并重新输入";  //检查输入信息是否有误，如果有误返回首页并提示
                req.setAttribute("inputError", inputError);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }

            System.out.print(country + "的疫情信息： ");
            System.out.print("累计确诊" + total + "  ");
            System.out.print("累计死亡" + dead + "  ");
            System.out.print("新增确诊" + today + "  ");
            System.out.print("危险等级" + level + "\n");

            rs.close();
            st.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        //*********************************************************************

        req.setAttribute("continent", continent); //传回处理后的信息给结果jsp
        req.setAttribute("country", country);
        req.setAttribute("total", total);
        req.setAttribute("dead", dead);
        req.setAttribute("today", today);
        req.setAttribute("level", level);
        req.setAttribute("update", update);
        req.getRequestDispatcher("/queryResult.jsp").forward(req, resp);
    }

    public String judge(String today) { //对危险等级进行判断
        int t = Integer.parseInt(today);
        String level = "wrong";
        if(t>10000)
            level = "红色";
        else if(t>5000)
            level = "橙色";
        else if(t>1000)
            level = "蓝色";
        else if(t>500)
            level = "浅蓝色";
        else
            level = "绿色";
        return level;
    }

    public String runPY() { //对数据库中的数据调用外部py程序进行更新，并返回验证结果
        Process proc;
        String update;
        try { //执行py文件
            proc = Runtime.getRuntime().exec("python C:\\pythoncrawler\\CovidAPICrawler\\Crawler.py");
            proc.waitFor();
            update = "数据更新成功！";
            System.out.println("数据更新成功!");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            update = "数据更新失败！";
            System.out.println("数据更新失败!");
        }
        return update;
    }
}
