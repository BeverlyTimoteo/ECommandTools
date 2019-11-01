package abvtools.componentes.busca;

import java.util.EventListener;

public abstract interface BuscaDadosListener extends EventListener {

    public void abrirConsulta(BuscaDadosEvento evt);

    public void finalizarConsulta(BuscaDadosEvento evt);

}
