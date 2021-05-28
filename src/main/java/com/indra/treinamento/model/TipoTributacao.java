package com.indra.treinamento.model;

import java.math.BigDecimal;

public enum TipoTributacao {

	ISENTO {
		@Override
		public boolean aliquotaInvalida(BigDecimal aliquotaImposto) {
			return aliquotaImposto.compareTo(new BigDecimal(0)) > 0;
		}
	}, TRIBUTAVEL {
		@Override
		public boolean aliquotaInvalida(BigDecimal aliquotaImposto) {
			return aliquotaImposto.equals(new BigDecimal(0));
		}
	};

	public abstract boolean aliquotaInvalida(BigDecimal aliquotaImposto);

}
