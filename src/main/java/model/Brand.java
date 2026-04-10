package model;

public class Brand {
    private String brandId;
    private String brandName;
    private String logo;

    public Brand() {}

    public Brand(String brandId, String brandName, String logo) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.logo = logo;
    }

    public String getBrandId() { return brandId; }
    public void setBrandId(String brandId) { this.brandId = brandId; }
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
}