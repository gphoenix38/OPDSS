package husseinm.opdss;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunModel {
    public Worker<String> worker;
    public AtomicBoolean shouldThrow = new AtomicBoolean(false);

    private int project_id;
    private ConnectionUtil connection;

    public static ArrayList<Graph2Model> graph2Models = new ArrayList<>();
    private ArrayList<GraphModel> graphModels;
    private ArrayList<Integer> ids = new ArrayList<>();

    public void init(int project_id, ConnectionUtil connection){
        this.project_id = project_id;
        this.connection = connection;

        graphModels = connection.getLines(project_id);
    }

    public RunModel() {
        worker = new Task<String>() {
            @Override
            protected String call() throws Exception {
                updateTitle("Example Task");
                updateMessage("Starting...");
                graph2Models.clear();
                updateProgress(0, graphModels.size());

                for (int i = 0; i < graphModels.size(); i++) {

                    ArrayList<Graph2Model> models = new ArrayList<>();
                    GraphModel graphModel = connection.getGraph(graphModels.get(i).getId());

                    double total_len = connection.getTotalLength(project_id);
                    ProjectModel projectModel = connection.getProject(project_id);

                    ArrayList<GraphModel> graphModels = new ArrayList<>();
                    graphModels.add(graphModel);
                    graphModels.addAll(connection.getFlowCont(graphModel.getUp_stream(), project_id));

                    double comm_flow = getCommFlow(getIds(graphModels),
                            total_len, projectModel.getDesign_discharge());

                    ArrayList<PipesModel> pipesModels = connection.getPipes(project_id);

                    for (PipesModel pipesModel : pipesModels) {
                        Graph2Model model = new Graph2Model();
                        model.setPipe_diameter(pipesModel.getDiameter());
                        model.setPipe_price(pipesModel.getPrice());
                        model.setAva_pipe_id(pipesModel.getId());
                        model.setComm_flow(comm_flow);
                        model.setP_id(graphModel.getId());
                        model.setPipe_id("P-"+(i+1));
                        model.setUs_elevation(connection.getPoint(graphModel.getUp_stream()).getElevation());
                        model.setDs_elevation(connection.getPoint(graphModel.getDown_stream()).getElevation());
                        model.setPipe_length(graphModel.getPipe_length());
                        model.setManning(projectModel.getManning());
                        model.setRoughness(projectModel.getRoughness());
                        model.setCover(projectModel.getNominal());
                        model.setMin_cover(projectModel.getMinimum());
                        model.setRate_excavation(projectModel.getExcavation());
                        model.setRate_backfilling(projectModel.getBack_filling());
                        model.setRate_material(projectModel.getMaterial());
                        model.setSlope();
                        models.add(model);
                    }

                    for (Graph2Model model: models){
                        boolean result = model.getCheck_slope().getValue().equalsIgnoreCase("feasible") && model.getCheck_min_velocity().getValue().equalsIgnoreCase("feasible");
                        if (result){
                            ids.add(i);
                            graph2Models.add(model);
                            break;
                        }
                    }

                    if (!ids.contains(i)){
                        Graph2Model model = new Graph2Model();
                        model.setP_id(graphModel.getId());
                        model.setPipe_id("P-"+(i+1));
                        model.setComm_flow(comm_flow);
                        model.setUs_elevation(connection.getPoint(graphModel.getUp_stream()).getElevation());
                        model.setDs_elevation(connection.getPoint(graphModel.getDown_stream()).getElevation());
                        model.setPipe_length(graphModel.getPipe_length());
                        model.setManning(projectModel.getManning());
                        model.setRoughness(projectModel.getRoughness());
                        model.setCover(projectModel.getNominal());
                        model.setMin_cover(projectModel.getMinimum());
                        model.setRate_excavation(projectModel.getExcavation());
                        model.setRate_backfilling(projectModel.getBack_filling());
                        model.setRate_material(projectModel.getMaterial());
                        model.setSlope();
                        graph2Models.add(model);
                    }
                    updateProgress(i, models.size());
                }
                return "Completed at " + System.currentTimeMillis();
            }

            @Override
            protected void scheduled() {
                System.out.println("The task is scheduled.");
            }

            @Override
            protected void running() {
                System.out.println("The task is running.");
            }
        };
        ((Task<String>) worker).setOnSucceeded(event -> {
            System.out.println("The task succeeded.");

        });
        ((Task<String>) worker).setOnCancelled(event -> {
            System.out.println("The task is canceled.");
        });
        ((Task<String>) worker).setOnFailed(event -> {
            System.out.println("The task failed.");
        });
    }

    private double getCommFlow(ArrayList<Integer> ids,
                               double total_len,
                               double design_discharge) {
        double total = 0.0;

        for (Integer integer : ids) {
            GraphModel model = connection.getGraph(integer);
            total += (model.getPipe_length() / total_len) * design_discharge;
        }

        return total;
    }

    private ArrayList<Integer> getIds(ArrayList<GraphModel> models) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (GraphModel model : models) {
            integers.add(model.getId());
        }

        return integers;
    }
}