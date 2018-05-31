package com.nkraft.eyebox.models.shit;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "SalesReport")
public class SalesReport {
    @PrimaryKey
    @SerializedName("idcode")
    private long id;
    private String delall;
    private String userlogs;
    private String sourcex;
    private String salestype;
    private String directoryx;
    @SerializedName("titlex")
    private String title;
    private String dispdis;
    private String dispvat;
    private String incvat;
    private String quantityx;
    private String quantityy;
    private String unitsx;
    private String unitsy;
    private String productsx;
    private String productsy;
    private String genericsx;
    private String genericsy;
    private String byx;
    private String byy;
    private String lotnox;
    private String lotnoy;
    private String expiryx;
    private String expiryy;
    private String pricex;
    private String pricey;
    private String amountx;
    private String amounty;
    private String divider;
    private String netx;
    private String nety;
    private String netlabx;
    private String netlaby;
    private String vaty;
    private String vatlabx;
    private String vatlaby;
    private String adjx;
    private String adjy;
    private String adjlabx;
    private String adjlaby;
    private String totalamountx;
    private String totalamounty;
    private String totalamountlabx;
    private String totalamountlaby;
    private String vatx;
    private String maxitems;
    private String clientx;
    private String clienty;
    private String clientaddx;
    private String clientaddy;
    private String clienttermsx;
    private String clienttermsy;
    private String salesdatex;
    private String salesdatey;
    private String checkedbyx;
    private String checkedbyy;
    private String deliveredbyx;
    private String deliveredbyy;
    private String decimalx;
    private String totalbeforevatx;
    private String totalbeforevaty;
    private String totalbeforevatlabx;
    private String totalbeforevatlaby;
    private String tinx;
    private String tiny;
    private String branchnamex;
    private String branchnamey;
    private String branchaddressx;
    private String branchaddressy;
    private String branchcontactnox;
    private String branchcontactnoy;
    private String salesidx;
    private String salesidy;
    private String seqx;
    private String seqy;
    private String deliverydatex;
    private String deliverydatey;
    @SerializedName("header")
    private String htmlHeader;
    @SerializedName("footer")
    private String htmlFooter;
    private String logo;
    private int filesizex;
    private String filetypex;
    private int filewidth;
    private int fileheight;
    private String filenamex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDelall() {
        return delall;
    }

    public void setDelall(String delall) {
        this.delall = delall;
    }

    public String getUserlogs() {
        return userlogs;
    }

    public void setUserlogs(String userlogs) {
        this.userlogs = userlogs;
    }

    public String getSourcex() {
        return sourcex;
    }

    public void setSourcex(String sourcex) {
        this.sourcex = sourcex;
    }

    public String getSalestype() {
        return salestype;
    }

    public void setSalestype(String salestype) {
        this.salestype = salestype;
    }

    public String getDirectoryx() {
        return directoryx;
    }

    public void setDirectoryx(String directoryx) {
        this.directoryx = directoryx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDispdis() {
        return dispdis;
    }

    public void setDispdis(String dispdis) {
        this.dispdis = dispdis;
    }

    public String getDispvat() {
        return dispvat;
    }

    public void setDispvat(String dispvat) {
        this.dispvat = dispvat;
    }

    public String getIncvat() {
        return incvat;
    }

    public void setIncvat(String incvat) {
        this.incvat = incvat;
    }

    public String getQuantityx() {
        return quantityx;
    }

    public void setQuantityx(String quantityx) {
        this.quantityx = quantityx;
    }

    public String getQuantityy() {
        return quantityy;
    }

    public void setQuantityy(String quantityy) {
        this.quantityy = quantityy;
    }

    public String getUnitsx() {
        return unitsx;
    }

    public void setUnitsx(String unitsx) {
        this.unitsx = unitsx;
    }

    public String getUnitsy() {
        return unitsy;
    }

    public void setUnitsy(String unitsy) {
        this.unitsy = unitsy;
    }

    public String getProductsx() {
        return productsx;
    }

    public void setProductsx(String productsx) {
        this.productsx = productsx;
    }

    public String getProductsy() {
        return productsy;
    }

    public void setProductsy(String productsy) {
        this.productsy = productsy;
    }

    public String getGenericsx() {
        return genericsx;
    }

    public void setGenericsx(String genericsx) {
        this.genericsx = genericsx;
    }

    public String getGenericsy() {
        return genericsy;
    }

    public void setGenericsy(String genericsy) {
        this.genericsy = genericsy;
    }

    public String getByx() {
        return byx;
    }

    public void setByx(String byx) {
        this.byx = byx;
    }

    public String getByy() {
        return byy;
    }

    public void setByy(String byy) {
        this.byy = byy;
    }

    public String getLotnox() {
        return lotnox;
    }

    public void setLotnox(String lotnox) {
        this.lotnox = lotnox;
    }

    public String getLotnoy() {
        return lotnoy;
    }

    public void setLotnoy(String lotnoy) {
        this.lotnoy = lotnoy;
    }

    public String getExpiryx() {
        return expiryx;
    }

    public void setExpiryx(String expiryx) {
        this.expiryx = expiryx;
    }

    public String getExpiryy() {
        return expiryy;
    }

    public void setExpiryy(String expiryy) {
        this.expiryy = expiryy;
    }

    public String getPricex() {
        return pricex;
    }

    public void setPricex(String pricex) {
        this.pricex = pricex;
    }

    public String getPricey() {
        return pricey;
    }

    public void setPricey(String pricey) {
        this.pricey = pricey;
    }

    public String getAmountx() {
        return amountx;
    }

    public void setAmountx(String amountx) {
        this.amountx = amountx;
    }

    public String getAmounty() {
        return amounty;
    }

    public void setAmounty(String amounty) {
        this.amounty = amounty;
    }

    public String getDivider() {
        return divider;
    }

    public void setDivider(String divider) {
        this.divider = divider;
    }

    public String getNetx() {
        return netx;
    }

    public void setNetx(String netx) {
        this.netx = netx;
    }

    public String getNety() {
        return nety;
    }

    public void setNety(String nety) {
        this.nety = nety;
    }

    public String getNetlabx() {
        return netlabx;
    }

    public void setNetlabx(String netlabx) {
        this.netlabx = netlabx;
    }

    public String getNetlaby() {
        return netlaby;
    }

    public void setNetlaby(String netlaby) {
        this.netlaby = netlaby;
    }

    public String getVaty() {
        return vaty;
    }

    public void setVaty(String vaty) {
        this.vaty = vaty;
    }

    public String getVatlabx() {
        return vatlabx;
    }

    public void setVatlabx(String vatlabx) {
        this.vatlabx = vatlabx;
    }

    public String getVatlaby() {
        return vatlaby;
    }

    public void setVatlaby(String vatlaby) {
        this.vatlaby = vatlaby;
    }

    public String getAdjx() {
        return adjx;
    }

    public void setAdjx(String adjx) {
        this.adjx = adjx;
    }

    public String getAdjy() {
        return adjy;
    }

    public void setAdjy(String adjy) {
        this.adjy = adjy;
    }

    public String getAdjlabx() {
        return adjlabx;
    }

    public void setAdjlabx(String adjlabx) {
        this.adjlabx = adjlabx;
    }

    public String getAdjlaby() {
        return adjlaby;
    }

    public void setAdjlaby(String adjlaby) {
        this.adjlaby = adjlaby;
    }

    public String getTotalamountx() {
        return totalamountx;
    }

    public void setTotalamountx(String totalamountx) {
        this.totalamountx = totalamountx;
    }

    public String getTotalamounty() {
        return totalamounty;
    }

    public void setTotalamounty(String totalamounty) {
        this.totalamounty = totalamounty;
    }

    public String getTotalamountlabx() {
        return totalamountlabx;
    }

    public void setTotalamountlabx(String totalamountlabx) {
        this.totalamountlabx = totalamountlabx;
    }

    public String getTotalamountlaby() {
        return totalamountlaby;
    }

    public void setTotalamountlaby(String totalamountlaby) {
        this.totalamountlaby = totalamountlaby;
    }

    public String getVatx() {
        return vatx;
    }

    public void setVatx(String vatx) {
        this.vatx = vatx;
    }

    public String getMaxitems() {
        return maxitems;
    }

    public void setMaxitems(String maxitems) {
        this.maxitems = maxitems;
    }

    public String getClientx() {
        return clientx;
    }

    public void setClientx(String clientx) {
        this.clientx = clientx;
    }

    public String getClienty() {
        return clienty;
    }

    public void setClienty(String clienty) {
        this.clienty = clienty;
    }

    public String getClientaddx() {
        return clientaddx;
    }

    public void setClientaddx(String clientaddx) {
        this.clientaddx = clientaddx;
    }

    public String getClientaddy() {
        return clientaddy;
    }

    public void setClientaddy(String clientaddy) {
        this.clientaddy = clientaddy;
    }

    public String getClienttermsx() {
        return clienttermsx;
    }

    public void setClienttermsx(String clienttermsx) {
        this.clienttermsx = clienttermsx;
    }

    public String getClienttermsy() {
        return clienttermsy;
    }

    public void setClienttermsy(String clienttermsy) {
        this.clienttermsy = clienttermsy;
    }

    public String getSalesdatex() {
        return salesdatex;
    }

    public void setSalesdatex(String salesdatex) {
        this.salesdatex = salesdatex;
    }

    public String getSalesdatey() {
        return salesdatey;
    }

    public void setSalesdatey(String salesdatey) {
        this.salesdatey = salesdatey;
    }

    public String getCheckedbyx() {
        return checkedbyx;
    }

    public void setCheckedbyx(String checkedbyx) {
        this.checkedbyx = checkedbyx;
    }

    public String getCheckedbyy() {
        return checkedbyy;
    }

    public void setCheckedbyy(String checkedbyy) {
        this.checkedbyy = checkedbyy;
    }

    public String getDeliveredbyx() {
        return deliveredbyx;
    }

    public void setDeliveredbyx(String deliveredbyx) {
        this.deliveredbyx = deliveredbyx;
    }

    public String getDeliveredbyy() {
        return deliveredbyy;
    }

    public void setDeliveredbyy(String deliveredbyy) {
        this.deliveredbyy = deliveredbyy;
    }

    public String getDecimalx() {
        return decimalx;
    }

    public void setDecimalx(String decimalx) {
        this.decimalx = decimalx;
    }

    public String getTotalbeforevatx() {
        return totalbeforevatx;
    }

    public void setTotalbeforevatx(String totalbeforevatx) {
        this.totalbeforevatx = totalbeforevatx;
    }

    public String getTotalbeforevaty() {
        return totalbeforevaty;
    }

    public void setTotalbeforevaty(String totalbeforevaty) {
        this.totalbeforevaty = totalbeforevaty;
    }

    public String getTotalbeforevatlabx() {
        return totalbeforevatlabx;
    }

    public void setTotalbeforevatlabx(String totalbeforevatlabx) {
        this.totalbeforevatlabx = totalbeforevatlabx;
    }

    public String getTotalbeforevatlaby() {
        return totalbeforevatlaby;
    }

    public void setTotalbeforevatlaby(String totalbeforevatlaby) {
        this.totalbeforevatlaby = totalbeforevatlaby;
    }

    public String getTinx() {
        return tinx;
    }

    public void setTinx(String tinx) {
        this.tinx = tinx;
    }

    public String getTiny() {
        return tiny;
    }

    public void setTiny(String tiny) {
        this.tiny = tiny;
    }

    public String getBranchnamex() {
        return branchnamex;
    }

    public void setBranchnamex(String branchnamex) {
        this.branchnamex = branchnamex;
    }

    public String getBranchnamey() {
        return branchnamey;
    }

    public void setBranchnamey(String branchnamey) {
        this.branchnamey = branchnamey;
    }

    public String getBranchaddressx() {
        return branchaddressx;
    }

    public void setBranchaddressx(String branchaddressx) {
        this.branchaddressx = branchaddressx;
    }

    public String getBranchaddressy() {
        return branchaddressy;
    }

    public void setBranchaddressy(String branchaddressy) {
        this.branchaddressy = branchaddressy;
    }

    public String getBranchcontactnox() {
        return branchcontactnox;
    }

    public void setBranchcontactnox(String branchcontactnox) {
        this.branchcontactnox = branchcontactnox;
    }

    public String getBranchcontactnoy() {
        return branchcontactnoy;
    }

    public void setBranchcontactnoy(String branchcontactnoy) {
        this.branchcontactnoy = branchcontactnoy;
    }

    public String getSalesidx() {
        return salesidx;
    }

    public void setSalesidx(String salesidx) {
        this.salesidx = salesidx;
    }

    public String getSalesidy() {
        return salesidy;
    }

    public void setSalesidy(String salesidy) {
        this.salesidy = salesidy;
    }

    public String getSeqx() {
        return seqx;
    }

    public void setSeqx(String seqx) {
        this.seqx = seqx;
    }

    public String getSeqy() {
        return seqy;
    }

    public void setSeqy(String seqy) {
        this.seqy = seqy;
    }

    public String getDeliverydatex() {
        return deliverydatex;
    }

    public void setDeliverydatex(String deliverydatex) {
        this.deliverydatex = deliverydatex;
    }

    public String getDeliverydatey() {
        return deliverydatey;
    }

    public void setDeliverydatey(String deliverydatey) {
        this.deliverydatey = deliverydatey;
    }

    public String getHtmlHeader() {
        return htmlHeader;
    }

    public void setHtmlHeader(String htmlHeader) {
        this.htmlHeader = htmlHeader;
    }

    public String getHtmlFooter() {
        return htmlFooter;
    }

    public void setHtmlFooter(String htmlFooter) {
        this.htmlFooter = htmlFooter;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getFilesizex() {
        return filesizex;
    }

    public void setFilesizex(int filesizex) {
        this.filesizex = filesizex;
    }

    public String getFiletypex() {
        return filetypex;
    }

    public void setFiletypex(String filetypex) {
        this.filetypex = filetypex;
    }

    public int getFilewidth() {
        return filewidth;
    }

    public void setFilewidth(int filewidth) {
        this.filewidth = filewidth;
    }

    public int getFileheight() {
        return fileheight;
    }

    public void setFileheight(int fileheight) {
        this.fileheight = fileheight;
    }

    public String getFilenamex() {
        return filenamex;
    }

    public void setFilenamex(String filenamex) {
        this.filenamex = filenamex;
    }
}
