package com.baticuisines.entity.componentType;

import com.baticuisines.entity.Component;

public class Labor extends Component {

    private double hourlyRate;
    private double workHours;
    private double workerProductivity;

    public Labor(int id, String name, String componentType, double TVARate , double hourlyRate, double workHours, double workerProductivity) {
        super(id, name , componentType , TVARate);
        this.hourlyRate = hourlyRate;
        this.workHours = workHours;
        this.workerProductivity = workerProductivity;
    }




    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double getWorkerProductivity() {
        return workerProductivity;
    }

    public void setWorkerProductivity(double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }

    @Override
    public String toString() {
        return "Labor{" +
                ", hourlyRate=" + hourlyRate +
                ", workHours=" + workHours +
                ", workerProductivity=" + workerProductivity +
                '}';
    }
}
