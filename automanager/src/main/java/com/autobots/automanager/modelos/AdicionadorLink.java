package com.autobots.automanager.modelos;

import java.util.List;

public interface AdicionadorLink<T> {
	void adicionarLink(T objeto);
	void adicionarLink(List<T> lista);
}
