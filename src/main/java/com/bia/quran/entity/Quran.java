package com.bia.quran.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.apache.solr.analysis.StopFilterFactory;
import org.apache.solr.analysis.SynonymFilterFactory;
import org.hibernate.annotations.Cache;
import static org.hibernate.annotations.CacheConcurrencyStrategy.NONSTRICT_READ_WRITE;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 *
 * @author Intesar Mohammed <mdshannan@gmail.com>
 */
@Entity
@Table(name = "Quran")
@Cache(usage = NONSTRICT_READ_WRITE)
@Indexed
@AnalyzerDefs({
    @AnalyzerDef(name = "custom-analyzer",
    tokenizer =
    @TokenizerDef(factory = StandardTokenizerFactory.class),
    filters = {
        @TokenFilterDef(factory = StandardFilterFactory.class),
        @TokenFilterDef(factory = StopFilterFactory.class),
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = SynonymFilterFactory.class, params = {
            @Parameter(name = "ignoreCase", value = "true"),
            @Parameter(name = "expand", value = "true"),
            @Parameter(name = "synonyms", value = "data/synonyms.properties")}),
        @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
            @Parameter(name = "language", value = "English")
        })
    })
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quran.findAll", query = "SELECT q FROM Quran q"),
    @NamedQuery(name = "Quran.findById", query = "SELECT q FROM Quran q WHERE q.id = :id"),
    @NamedQuery(name = "Quran.findBySuraId", query = "SELECT q FROM Quran q WHERE q.suraId = :suraId ORDER BY q.verseId"),
    @NamedQuery(name = "Quran.findByVerseId", query = "SELECT q FROM Quran q WHERE q.verseId = :verseId")
})
public class Quran implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // ayah number
    @Field
    @NumericField
    @Column(name = "ayahId")
    private Integer ayahId;
    // ayah
    @Field
    @Analyzer(definition = "custom-analyzer")
    @Lob
    @Column(name = "ayahText")
    private String ayahText;
    // sura numbber
    @Field
    @Column(name = "suraId")
    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private Surah surah;
    // verse number
    @Field
    @NumericField
    @Column(name = "verseId")
    private Integer verseId;

    public Quran() {
    }

    public Quran(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAyahId() {
        return ayahId;
    }

    public void setAyahId(Integer ayahId) {
        this.ayahId = ayahId;
    }

    public String getAyahText() {
        return ayahText;
    }

    public void setAyahText(String ayahText) {
        this.ayahText = ayahText;
    }

    public Surah getSurah() {
        return surah;
    }

    public void setSurah(Surah surah) {
        this.surah = surah;
    }

    public Integer getVerseId() {
        return verseId;
    }

    public void setVerseId(Integer verseId) {
        this.verseId = verseId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quran)) {
            return false;
        }
        Quran other = (Quran) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Quran{" + "id=" + id + ", ayahId=" + ayahId + ", ayahText=" + ayahText + ", surah=" + surah + ", verseId=" + verseId + '}';
    }
}
