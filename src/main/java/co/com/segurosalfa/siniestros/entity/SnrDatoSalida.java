package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
@Table(name = "SNR_DATO_SALIDA")
public class SnrDatoSalida implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TIPO_TRANSACCION")
	private String ttraab;
	
	@Column(name = "COD_COMPANIA")
	private String cciaab;
	
	@Column(name = "ANIO_ECONOMICO")
	private Long aniocab;
	
	@Column(name = "PERIODO_COMPROBANTE")
	private Long lapsab;
	
	@Column(name = "TIPO_COMPROBANTE")
	private Long ticoab;
	
	@Column(name = "NUM_COMPROBANTE")
	private Long nucoab;
	
	@Column(name = "CUENTA_MAYOR")
	private String cuenab;
	
	@Column(name = "CUENTA_AUXILIAR")
	private String cauxab;
	
	@Column(name = "DESCRIPCION_ASIENTO")
	private String descab;
	
	@Column(name = "IMPORTE_ASIENTO")
	private Long montab;
	
	@Column(name = "TIPO_MOVIMIENTO")
	private String tmovab;
	
	@Column(name = "IMPORTE_2DA_MONEDA")
	private Long nosmab;
	
	@Column(name = "CLASE_DOCUMENTO")
	private String cldoab;
	
	@Column(name = "NUM_DOCUMENTO")
	private Long ndocab;
	
	@Column(name = "FECHA_DOCUMENTO")
	private Long fdocab;
	
	@Column(name = "FECHA_VENCIMIENTO")
	private Long feveab;
	
	@Column(name = "TIPO_INGRESO_EGRESO")
	private String teinab;
	
	@Column(name = "NUM_IDENTIFICACION_TRIB")
	private String nnitab;
	
	@Column(name = "AUX_ADICIONAL_1")
	private String aua1ab;
	
	@Column(name = "AUX_ADICIONAL_2")
	private String aua2ab;
	
	@Column(name = "CLASE_DOCUMENTO_2")
	private String cddoab;
	
	@Column(name = "NUM_DOCUMENTO_2")
	private Long nddoab;
	
	@Column(name = "TERCER_IMPORTE")
	private Long tercab;
	
	@Column(name = "CODIGO_IVA")
	private String cdivab;

}
