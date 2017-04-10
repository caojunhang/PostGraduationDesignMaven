package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the K_BOOK_TRAIN database table.
 * 
 */
@Entity
@Table(name="K_BOOK_TRAIN")
public class KBookTrainObj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date actualpublishingtime;

	private BigDecimal aper;

	private String author;

	private String bookclassification;

	private String bookmainname;

	private String bookname;

	private BigDecimal bookprice;

	private String bookyear;

	private String boundform;

	private String cata1;

	private String cata2;

	private String cata3;

	private BigDecimal charnum;

	private String contentsummary;

	private String fisrtauthor;

	private String fisrtauthortype;

	private String guidingclassificationcontent;

	@Id
	private BigDecimal id;

	private BigDecimal impression;

	private String isbn;

	private String issuescope;

	@Column(name="\"LANGUAGE\"")
	private String language;

	private String languagetype;

	private String locationpublishers;

	private String newbooktime;

	private BigDecimal numberofprints;

	private BigDecimal pagenum;

	@Temporal(TemporalType.DATE)
	private Date plannedpublicationtime;

	private BigDecimal pper;

	private BigDecimal pricebook;

	private String printingunit;

	private BigDecimal printsnum;

	private BigDecimal printssum;

	private String productsize;

	private String publicationway;

	private String publish;

	private String publishername;

	private String reader;

	private BigDecimal revision;

	private BigDecimal saleeveryyear;

	private String topiccata;

	private String typecontent;

	public KBookTrainObj() {
	}

	public Date getActualpublishingtime() {
		return this.actualpublishingtime;
	}

	public void setActualpublishingtime(Date actualpublishingtime) {
		this.actualpublishingtime = actualpublishingtime;
	}

	public BigDecimal getAper() {
		return this.aper;
	}

	public void setAper(BigDecimal aper) {
		this.aper = aper;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookclassification() {
		return this.bookclassification;
	}

	public void setBookclassification(String bookclassification) {
		this.bookclassification = bookclassification;
	}

	public String getBookmainname() {
		return this.bookmainname;
	}

	public void setBookmainname(String bookmainname) {
		this.bookmainname = bookmainname;
	}

	public String getBookname() {
		return this.bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public BigDecimal getBookprice() {
		return this.bookprice;
	}

	public void setBookprice(BigDecimal bookprice) {
		this.bookprice = bookprice;
	}

	public String getBookyear() {
		return this.bookyear;
	}

	public void setBookyear(String bookyear) {
		this.bookyear = bookyear;
	}

	public String getBoundform() {
		return this.boundform;
	}

	public void setBoundform(String boundform) {
		this.boundform = boundform;
	}

	public String getCata1() {
		return this.cata1;
	}

	public void setCata1(String cata1) {
		this.cata1 = cata1;
	}

	public String getCata2() {
		return this.cata2;
	}

	public void setCata2(String cata2) {
		this.cata2 = cata2;
	}

	public String getCata3() {
		return this.cata3;
	}

	public void setCata3(String cata3) {
		this.cata3 = cata3;
	}

	public BigDecimal getCharnum() {
		return this.charnum;
	}

	public void setCharnum(BigDecimal charnum) {
		this.charnum = charnum;
	}

	public String getContentsummary() {
		return this.contentsummary;
	}

	public void setContentsummary(String contentsummary) {
		this.contentsummary = contentsummary;
	}

	public String getFisrtauthor() {
		return this.fisrtauthor;
	}

	public void setFisrtauthor(String fisrtauthor) {
		this.fisrtauthor = fisrtauthor;
	}

	public String getFisrtauthortype() {
		return this.fisrtauthortype;
	}

	public void setFisrtauthortype(String fisrtauthortype) {
		this.fisrtauthortype = fisrtauthortype;
	}

	public String getGuidingclassificationcontent() {
		return this.guidingclassificationcontent;
	}

	public void setGuidingclassificationcontent(String guidingclassificationcontent) {
		this.guidingclassificationcontent = guidingclassificationcontent;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getImpression() {
		return this.impression;
	}

	public void setImpression(BigDecimal impression) {
		this.impression = impression;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIssuescope() {
		return this.issuescope;
	}

	public void setIssuescope(String issuescope) {
		this.issuescope = issuescope;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguagetype() {
		return this.languagetype;
	}

	public void setLanguagetype(String languagetype) {
		this.languagetype = languagetype;
	}

	public String getLocationpublishers() {
		return this.locationpublishers;
	}

	public void setLocationpublishers(String locationpublishers) {
		this.locationpublishers = locationpublishers;
	}

	public String getNewbooktime() {
		return this.newbooktime;
	}

	public void setNewbooktime(String newbooktime) {
		this.newbooktime = newbooktime;
	}

	public BigDecimal getNumberofprints() {
		return this.numberofprints;
	}

	public void setNumberofprints(BigDecimal numberofprints) {
		this.numberofprints = numberofprints;
	}

	public BigDecimal getPagenum() {
		return this.pagenum;
	}

	public void setPagenum(BigDecimal pagenum) {
		this.pagenum = pagenum;
	}

	public Date getPlannedpublicationtime() {
		return this.plannedpublicationtime;
	}

	public void setPlannedpublicationtime(Date plannedpublicationtime) {
		this.plannedpublicationtime = plannedpublicationtime;
	}

	public BigDecimal getPper() {
		return this.pper;
	}

	public void setPper(BigDecimal pper) {
		this.pper = pper;
	}

	public BigDecimal getPricebook() {
		return this.pricebook;
	}

	public void setPricebook(BigDecimal pricebook) {
		this.pricebook = pricebook;
	}

	public String getPrintingunit() {
		return this.printingunit;
	}

	public void setPrintingunit(String printingunit) {
		this.printingunit = printingunit;
	}

	public BigDecimal getPrintsnum() {
		return this.printsnum;
	}

	public void setPrintsnum(BigDecimal printsnum) {
		this.printsnum = printsnum;
	}

	public BigDecimal getPrintssum() {
		return this.printssum;
	}

	public void setPrintssum(BigDecimal printssum) {
		this.printssum = printssum;
	}

	public String getProductsize() {
		return this.productsize;
	}

	public void setProductsize(String productsize) {
		this.productsize = productsize;
	}

	public String getPublicationway() {
		return this.publicationway;
	}

	public void setPublicationway(String publicationway) {
		this.publicationway = publicationway;
	}

	public String getPublish() {
		return this.publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getPublishername() {
		return this.publishername;
	}

	public void setPublishername(String publishername) {
		this.publishername = publishername;
	}

	public String getReader() {
		return this.reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public BigDecimal getRevision() {
		return this.revision;
	}

	public void setRevision(BigDecimal revision) {
		this.revision = revision;
	}

	public BigDecimal getSaleeveryyear() {
		return this.saleeveryyear;
	}

	public void setSaleeveryyear(BigDecimal saleeveryyear) {
		this.saleeveryyear = saleeveryyear;
	}

	public String getTopiccata() {
		return this.topiccata;
	}

	public void setTopiccata(String topiccata) {
		this.topiccata = topiccata;
	}

	public String getTypecontent() {
		return this.typecontent;
	}

	public void setTypecontent(String typecontent) {
		this.typecontent = typecontent;
	}

}