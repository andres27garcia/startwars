package co.com.segurosalfa.siniestros.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SNR_DATO_BONO")
public class SnrDatoBono implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NUM_PERSONA", nullable = false)
	private Long numPersona;

	@Column(name = "FEC_TRASLADO")
	private LocalDateTime fecTraslado;

	@Column(name = "VLR_FEC_TRASLADO")
	private BigDecimal vlrFecTraslado;

	@Column(name = "COD_ESTADO_BONO")
	private String codEstadoBono;

	@Column(name = "FEC_ACREDITACION")
	private LocalDateTime fecAcreditacion;

	@Column(name = "FEC_SINIESTRO")
	private LocalDateTime fecSiniestro;

	@Column(name = "FEC_CALCULO")
	private LocalDateTime FecCalculo;

	@Column(name = "VLR_FEC_SINIESTRO")
	private BigDecimal vlrFecSiniestro;

	@Column(name = "FEC_EMISION")
	private LocalDateTime fecEmision;

	@Column(name = "VLR_EMISION")
	private BigDecimal vlrEmision;

	@Column(name = "FEC_REDENCION")
	private LocalDateTime fecRedencion;

	@Column(name = "VLR_REDENCION")
	private BigDecimal vlrRedencion;

	@Column(name = "COD_TIPO_BONO")
	private String codTipoBono;

	@Column(name = "VLR_ACREDITACION")
	private BigDecimal vlrAcreditacion;

	@Column(name = "VLR_CUPON_PPAL_EMISION")
	private BigDecimal vlrCuponPpalEmision;

	@Column(name = "VLR_CUPON_PPAL_REDENCION")
	private BigDecimal vlrCuponPpalRedencion;

	@Column(name = "VLR_CUOTA_PARTE_SINI")
	private BigDecimal vlrCuotaParteSini;

	@Column(name = "VLR_CUOTA_PARTE_TRAS")
	private BigDecimal vlrCuotaParteTras;

	@Column(name = "VLR_CUPON_PRINCIPAL_SINI")
	private BigDecimal vlrCuponPrincipalSini;

	@Column(name = "VLR_CUPON_PRINCIPAL_TRAS")
	private BigDecimal vlrCuponPrincipalTras;

	@Column(name = "VLR_FEC_CALCULO")
	private BigDecimal vlrFecCalculo;

}
