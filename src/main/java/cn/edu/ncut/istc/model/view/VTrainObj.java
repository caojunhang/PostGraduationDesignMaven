package cn.edu.ncut.istc.model.view;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the V_TRAIN database table.
 * 
 */
@Entity
@Table(name="V_TRAIN")
public class VTrainObj implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal aper;

	private String author;

	private String bookname;

	private BigDecimal bookprice;

	private String boundform;

	private String cata2;

	private String cata3;

	private BigDecimal charnum;

	private String contentsummary;

	@Id
	private BigDecimal id;

	private BigDecimal impression;

	private String languagetype;

	private String locationpublishers;

	//private String newbooktime;

	private BigDecimal pagenum;

	private BigDecimal pper;

	private String publicationway;

	private String reader;

	private BigDecimal revision;

	private BigDecimal saleeveryyear;

	private String topiccata;

	private String typecontent;
	
	private String fisrtauthor;
	
	public String getFisrtauthor() {
		return fisrtauthor;
	}

	public void setFisrtauthor(String fisrtauthor) {
		this.fisrtauthor = fisrtauthor;
	}

	public VTrainObj() {
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

	public String getBoundform() {
		return this.boundform;
	}

	public void setBoundform(String boundform) {
		this.boundform = boundform;
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

//	public String getNewbooktime() {
//		return this.newbooktime;
//	}
//
//	public void setNewbooktime(String newbooktime) {
//		this.newbooktime = newbooktime;
//	}

	public BigDecimal getPagenum() {
		return this.pagenum;
	}

	public void setPagenum(BigDecimal pagenum) {
		this.pagenum = pagenum;
	}

	public BigDecimal getPper() {
		return this.pper;
	}

	public void setPper(BigDecimal pper) {
		this.pper = pper;
	}

	public String getPublicationway() {
		return this.publicationway;
	}

	public void setPublicationway(String publicationway) {
		this.publicationway = publicationway;
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