package test;

/**
 * Created by zhuchao on 18-6-21.
 */
public class Edb2In {
    private String name;
    private String conns;
    private String minIdle;
    private String maxIdle;
    private String maxActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConns() {
        return conns;
    }

    public void setConns(String conns) {
        this.conns = conns;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }
}
