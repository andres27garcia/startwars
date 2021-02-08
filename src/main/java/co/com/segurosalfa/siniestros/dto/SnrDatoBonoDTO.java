package co.com.segurosalfa.siniestros.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnrDatoBonoDTO implements Serializable {

	private static final long serialVersionUID = -4193115601730357241L;

	private Long numPersona;
	private LocalDateTime fecTraslado;
	private BigDecimal vlrFecTraslado;
	private String codEstadoBono;
	private LocalDateTime fecAcreditacion;
	private LocalDateTime fecSiniestro;
	private LocalDateTime FecCalculo;
	private BigDecimal vlrFecSiniestro;
	private LocalDateTime fecEmision;
	private BigDecimal vlrEmision;
	private LocalDateTime fecRedencion;
	private BigDecimal vlrRedencion;
	private String codTipoBono;
	private BigDecimal vlrAcreditacion;
	private BigDecimal vlrCuponPpalEmision;
	private BigDecimal vlrCuponPpalRedencion;
	private BigDecimal vlrCuotaParteSini;
	private BigDecimal vlrCuotaParteTras;
	private BigDecimal vlrCuponPrincipalSini;
	private BigDecimal vlrCuponPrincipalTras;
	private BigDecimal vlrFecCalculo;

}
