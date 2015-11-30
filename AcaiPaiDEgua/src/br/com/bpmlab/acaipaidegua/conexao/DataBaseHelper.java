package br.com.bpmlab.acaipaidegua.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.bpmlab.acaipaidegua.entidade.Estabelecimento;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper<E> extends OrmLiteSqliteOpenHelper {
	// nome do banco de dados(o ponto bd nao e obrigatorio)
	// Esses dois atributos devem ter sempre esses mesmo nome, pois
	// estao sendo passados como parametros no construtor da classe
	private static final String databaseName = "acai.bd";
	// Versao desse BD
	private static final int databaseVersion = 17;
	// private static Cursor cursor = openOrCreateDatabase("batedores.db",
	// Context.MODE_PRIVATE, null) ;

	private static Context cont;

	private List<StringBuilder> lerScript(String arquivo) {
		List<StringBuilder> resposta = new ArrayList<StringBuilder>();
		try {
			InputStream is = cont.getAssets().open(arquivo);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String linha = null;
			StringBuilder sb = null;

			while ((linha = br.readLine()) != null) {

				if (linha.trim().equals("--INICIO")) {
					sb = new StringBuilder();

				} else if (linha.trim().equals("--FIM")) {
					resposta.add(sb);

				} else {
					if (!linha.trim().isEmpty()) {
						sb.append(linha);

					}
				}
			}
			br.close();
		} catch (IOException e) {
			Log.e("SCRIPT", "Erro ao ler script: " + e);
			e.printStackTrace();
		}
		return resposta;
	}

	// Conecta com o BD, esse construtor verifica se o bd existe, se nao cria o
	// BD e se existir deleta e cria novamente se for necessario.
	public DataBaseHelper(Context context) {
		super(context, databaseName, null, databaseVersion);
		// TODO Auto-generated constructor stub
		cont = context;
	}

	private void realizarInserts(SQLiteDatabase db) {
		List<StringBuilder> inserts = lerScript("inserts.sql");
		for (StringBuilder sb : inserts) {
			db.execSQL(sb.toString());
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource src) {
		try {
			TableUtils.createTable(src, Estabelecimento.class);
			realizarInserts(db);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource src,
			int oldVersion, int newVersion) {
		try {
			
			
				db.execSQL("DELETE FROM estabelecimento WHERE id > 0");			
				realizarInserts(db);
				
			
			Log.i("Atualização","Banco atualizado com sucesso!");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("ERRO!", "Erro ao atualizar banco");
		}

	}

	@Override
	public void close() {
		super.close();
	}
}
