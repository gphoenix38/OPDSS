package husseinm.opdss;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class ConnectionUtil {

    private Connection connection;

    public ConnectionUtil() {
        connectDb();
    }

    public void closeDb() {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * The database file found in database folder must be put inside user.home/OPDSS/database manually
     */
    public void connectDb() {
        try {
            if (connection!=null && !connection.isClosed())
                return;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + new File(System.getProperty("user.home") + File.separator + "OPDSS" +
                    File.separator + "database" + File.separator + "opdss.sqlite").getAbsolutePath());
            System.out.println("connect");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" not connect");
        }
    }

    public boolean addProject(String name) {
        String stat = "insert into project_tbl(name, rgb) values ('" + name + "','" + MainController.LabelCell.getRandomColor() + "," + MainController.LabelCell.getRandomColor() + "," + MainController.LabelCell.getRandomColor() + "')";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addProject(ProjectModel projectModel) {
        String stat = "insert into project_tbl(name, rgb, manning, roughness, " +
                "nominal_cover, min_cover, excavation, backfilling, " +
                "material, design_discharge) values ('" + projectModel.getName() + "'," +
                "'" + MainController.LabelCell.getRandomColor() + "," + MainController.LabelCell.getRandomColor() + "," + MainController.LabelCell.getRandomColor() + "'," +
                "'" + projectModel.getManning() + "', '" + projectModel.getRoughness() + "'," +
                "'" + projectModel.getNominal() + "', '" + projectModel.getMinimum() + "'," +
                "'" + projectModel.getExcavation() + "', '" + projectModel.getBack_filling() + "'," +
                "'" + projectModel.getMaterial() + "', '" + projectModel.getDesign_discharge() + "')";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateProject(ProjectModel projectModel, int project_id) {
        String stat = "UPDATE project_tbl SET name = '" + projectModel.getName() + "'," +
                "manning = '" + projectModel.getManning() + "'," +
                "roughness = '" + projectModel.getRoughness() + "'," +
                "nominal_cover = '" + projectModel.getNominal() + "'," +
                "min_cover = '" + projectModel.getMinimum() + "'," +
                "excavation = '" + projectModel.getExcavation() + "'," +
                "backfilling = '" + projectModel.getBack_filling() + "'," +
                "material = '" + projectModel.getMaterial() + "'" +
                " WHERE id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateDesignDischarge(String dd, int project_id) {
        String stat = "UPDATE project_tbl SET design_discharge = '" + dd + "' " +
                " WHERE id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addPipes(ProjectModel projectModel, int project_id) {
        String stat = "insert into commerical_pipes(diameter, price, project_id) values ('" + projectModel.getDiameter() + "'," +
                "'" + projectModel.getPrice() + "', '" + project_id + "')";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addPoints(Point point) {
        String stat = "insert into points_tbl(x, y, elevation, project_id) values ('" + point.getX() + "'," +
                "'" + point.getY() + "', '" + point.getElevation() + "', '" + point.getProject_id() + "')";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updatePoint(Point point) {
        String stat = "UPDATE points_tbl SET elevation = '"+point.getElevation()+"' WHERE project_id = '" + point.getProject_id() + "'" +
                "AND x = '" + point.getX() + "' AND y = '" + point.getY() + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addLine(GraphModel graphModel) {
        String stat = "insert into graph_tbl(pipe_length, up_stream ,down_stream, slope, project_id) values ('" + graphModel.getPipe_length() + "'," +
                "'" + graphModel.getUp_stream() + "', '" + graphModel.getDown_stream() + "', '" + graphModel.getSlope() + "', '" + graphModel.getProject_id() + "')";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public int getProject(String name) {
        String stat = "SELECT * FROM project_tbl WHERE name = '" + name + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ProjectModel getProject(int id) {
        String stat = "SELECT * FROM project_tbl WHERE id = '" + id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new ProjectModel(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("created_at"),
                        resultSet.getString("rgb"),
                        resultSet.getDouble("manning"),
                        resultSet.getDouble("roughness"),
                        resultSet.getDouble("nominal_cover"),
                        resultSet.getDouble("min_cover"),
                        resultSet.getDouble("excavation"),
                        resultSet.getDouble("backfilling"),
                        resultSet.getDouble("material"),
                        resultSet.getDouble("design_discharge")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Point> getPoints(int project_id) {
        ArrayList<Point> list = new ArrayList<>();
        String stat = "SELECT * FROM points_tbl WHERE project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            int sn = 1;
            while (resultSet.next()) {
                list.add(new Point(
                        resultSet.getInt("id"),
                        sn,
                        resultSet.getDouble("x"),
                        resultSet.getDouble("y"),
                        resultSet.getDouble("elevation"),
                        resultSet.getInt("project_id")
                ));
                sn++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<PipesModel> getPipes(int project_id) {
        ArrayList<PipesModel> list = new ArrayList<>();
        String stat = "SELECT * FROM commerical_pipes WHERE project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new PipesModel(
                        resultSet.getInt("id"),
                        resultSet.getDouble("diameter"),
                        resultSet.getDouble("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<ProjectModel> getProPipes(int project_id) {
        ArrayList<ProjectModel> list = new ArrayList<>();
        String stat = "SELECT * FROM commerical_pipes WHERE project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new ProjectModel(
                        resultSet.getInt("id"),
                        resultSet.getDouble("diameter"),
                        resultSet.getDouble("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deletePipes(int project_id) {
        String stat = "DELETE FROM commerical_pipes WHERE project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteGraph(int project_id) {
        String stat = "DELETE FROM graph_tbl WHERE project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePoints(int project_id) {
        String stat = "DELETE FROM points_tbl WHERE project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProject(int project_id) {
        String stat = "DELETE FROM project_tbl WHERE id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Point getPoint(int id) {
        String stat = "SELECT * FROM points_tbl WHERE id = '" + id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Point(
                        resultSet.getInt("id"),
                        0,
                        resultSet.getDouble("x"),
                        resultSet.getDouble("y"),
                        resultSet.getDouble("elevation"),
                        resultSet.getInt("project_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<GraphModel> getLines(int project_id) {
        ArrayList<GraphModel> list = new ArrayList<>();
        String stat = "SELECT * FROM graph_tbl WHERE project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            int sn = 1;
            while (resultSet.next()) {
                list.add(new GraphModel(
                        resultSet.getInt("id"),
                        sn,
                        resultSet.getDouble("pipe_length"),
                        resultSet.getInt("up_stream"),
                        resultSet.getInt("down_stream"),
                        resultSet.getString("slope"),
                        resultSet.getInt("project_id")
                ));
                sn++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public GraphModel getGraph(int id) {

        String stat = "SELECT * FROM graph_tbl WHERE id = '" + id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            int sn = 1;
            if (resultSet.next()) {
                return new GraphModel(
                        resultSet.getInt("id"),
                        sn,
                        resultSet.getDouble("pipe_length"),
                        resultSet.getInt("up_stream"),
                        resultSet.getInt("down_stream"),
                        resultSet.getString("slope"),
                        resultSet.getInt("project_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<GraphModel> getFlowCont(int up_id, int project_id) {
        ArrayList<GraphModel> list = new ArrayList<>();
        String stat = "SELECT * FROM graph_tbl WHERE project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            int sn = 1;
            while (resultSet.next()) {
                if (resultSet.getInt("down_stream") == up_id) {
                    list.add(new GraphModel(
                            resultSet.getInt("id"),
                            sn,
                            resultSet.getDouble("pipe_length"),
                            resultSet.getInt("up_stream"),
                            resultSet.getInt("down_stream"),
                            resultSet.getString("slope"),
                            resultSet.getInt("project_id")
                    ));
                }
                sn++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public double getTotalLength(int project_id) {
        Double total = 0.0;
        String stat = "SELECT * FROM graph_tbl WHERE project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                total += resultSet.getDouble("pipe_length");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public boolean projectExists(String name) {
        String stat = "SELECT * FROM project_tbl WHERE name = '" + name + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean pointExists(double x, double y) {
        String stat = "SELECT * FROM points_tbl WHERE x = '" + x + "' AND y = '" + y + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean lineExists(int up_id, int down_id, int project_id) {
        String stat = "SELECT * FROM graph_tbl WHERE up_stream = '" + up_id + "' AND down_stream = '" + down_id + "'" +
                "AND project_id = '" + project_id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<ProjectModel> getProjects() {
        ArrayList<ProjectModel> names = new ArrayList<>();
        String stat = "SELECT * FROM project_tbl";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                names.add(
                        new ProjectModel(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("created_at"),
                                resultSet.getString("rgb")
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return names;
    }

    public void insertXYPoint(double x, double y, int project_id) {

        String stat = "INSERT  INTO graph_tbl(x1, y1, project_id) values('" + x + "', '" + y + "', '" + project_id + "')";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertXYPoint2(double x, double y, int project_id,
                               int id) {

        String stat = "UPDATE graph_tbl SET x2 = '" + x + "', y2='" + y + "' WHERE project_id = '" + project_id + "' AND  id = '" + id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stat);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(String sql) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
