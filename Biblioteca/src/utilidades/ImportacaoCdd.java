/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import classes.AreaConhecimento;
import controle.ControleAreaConhecimento;
import interfaces.ICRUDAreaConhecimento;

/**
 *
 * @author vovostudio
 */
public class ImportacaoCdd {

    ICRUDAreaConhecimento controleAreaConhecimento = null;
    AreaConhecimento area = null;

    String[][] tabelaInicial = {
        {"340", "Direito / Jurisprudência / Justiça"},
        {"34207", "Código Eleitoral"},
        {"3425981", "Código de Defesa do Consumidor;Brasil"},
        {"3430946", "Código de Transito"},
        {"34003", "Dicionários e enciclopédias de direito"},
        {"34007", "Direito;Estudo e ensino"},
        {"3402", "Comparação entre tipos de direito"},
        {"3405", "Sistemas de direito ( Leis antigas, romanas, medievais, orientais, islâmicas )"},
        {"341", "Direito internacional"},
        {"341026", "Tratados"},
        {"3411", "Fontes de direito internacional  ( Tratados, decisões judiciais, prática, princípios )"},
        {"3412", "Comunidade mundial"},
        {"34122", "Liga das Nações"},
        {"34123", "Nações Unidas"},
        {"34124", "Organizações regionais ( nas diversas partes do mundo )"},
        {"3412422", "Comunidade Econômica Européia (CEE) Mercado Comum Europeu União Européia"},
        {"3413", "Relações entre países / Leis diplomáticas, consulares, tratados ) "},
        {"3414", "Relações jurisdicionais entre países ( Território, águas, rios, lagos, canais, mares, espaço aéreo, espaço extraterrestre)"},
        {"341481", "Direitos humanos / Direitos civis"},
        {"3415", "Disputas e conflitos entre países ( mediação de paz, cortes judiciais, ultimatos, coerções, sanções, intervenções)"},
        {"3416", "Leis de guerra ( agressões, hostilidades, início de guerra )"},
        {"34169", "Crimes de guerra"},
        {"3417", "Cooperação internacional ( Defesa civil, forças de paz, missões militares, paz e desarmamento )"},
        {"34173", "Paz  (direito internacional / conferências para a paz )"},
        {"341734", "Controle de armas nucleares"},
        {"341735", "Controle de armas químicas e biológicas"},
        {"341736", "Controle de armas estratégicas em tempo de paz"},
        {"34175", "Direito econômico internacional"},
        {"341751", "Direito financeiro internacional"},
        {"341752", "Investimentos"},
        {"341753", "Condições de negócios  ( monopólios, contratos )"},
        {"341754", "Comércio / Tarifas / Turismo"},
        {"341756", "Transportes ( aéreo, naval, fluvial , rodoviário e ferroviário )"},
        {"341757", "Comunicações ( postal, telecomunicação, comunicação por satélites )"},
        {"341758", "Propriedade intelectual / industrial / marcas / licenças / franchising /copyright / design / patentes )"},
        {"341759", "Desenvolvimento social e econômico"},
        {"34176", "Relações sociais e culturais ( Seguridade social / UNICEF / Controle  da saúde / ecologia / saúde pública / ajuda internacional )"},
        {"34177", "Legislação criminal internacional"},
        {"341773", "Terrorismo"},
        {"341775", "Tráfico de drogas"},
        {"341778", "Genocídio"},
        {"34178", "Cooperação judicial internacional"},
        {"342", "Direito constitucional e administrativo"},
        {"34202", "Instrumentos básicos de governo;Constituição"},
        {"3420281", "Constituição do Brasil"},
        {"34203", "Reformas constitucionais"},
        {"34204", "Poder e função do governo ( conduta em relação a outros países, jurisdição  fora do território, colônias, força policial para controle da segurança nacional, saúde pública, etc., níveis de governo )"},
        {"34205", "Poder legislativo"},
        {"34206", "Poder executivo"},
        {"34208", "Jurisdição sobre pessoas  ( incluindo censura)"},
        {"342082", "Entrada e saída do país / Emigração, imigração, passaportes ,vistos 342.083;Cidadania,   nacionalidade  (incluindo direito de asilo )"},
        {"342084", "Aborto"},
        {"342085", "Direitos e atividades individuais ( religiosas, liberdade de informação, da palavra, de opinião, de imprensa, opinião política, privacidade)"},
        {"342087", "Grupos sociais ( índios, grupos pela raça, etnia, nacionalidade, homens, mulheres)"},
        {"342088", "Responsabilidade do governo ( incluindo responsabilidade por abuso de poder, corrupção, etc.)"},
        {"34209", "Governo municipal ( corporações )"},
        {"3421", "Regiões sócioeconômicas"},
        {"34239", "Jurisdições e áreas  (Tab.2) Ex.: Lei eleitoral na Austrália"},
        {"34301", "Direito militar / Defesa / Segurança nacional / Legislação de emergência e de guerra;Veteranos ( legislação, pensão, educação e treino, empregos, planos de saúde e reabilitação , compensação por doenças)"},
        {"343012", "Força humana ( exército) Alistamento, destacamentos."},
        {"343013", "Serviço militar"},
        {"343014", "Disciplina e conduta ( procedimentos legais)"},
        {"34302", "Legislação de propriedade ;pública  e pessoal ( aquisição, controle, uso, venda e transmissão )"},
        {"34303", "Legislação das finanças públicas ( política monetária, orçamento, receita, gastos, etc.)"},
        {"34304", "Legislação fiscal / Impostos (  municipal, estadual e federal)"},
        {"34305", "Tipos de impostos  (imposto de renda, profissão, capital, investimentos , mercadorias , taxas, serviços, selos, etc.)"},
        {"34307", "Regulamentação das atividades econômicas(proteção ao consumidor/ espionagem industrial / Lei antitrust  / fixação de preços,  etc.)"},
        {"34374", "Assistência econômica doméstica, desenvolvimento rural, regional,"},
        {"34375", "Controle de produção"},
        {"34376", "Agricultura e indústrias agrícolas"},
        {"34377", "Indústrias minerais"},
        {"34378", "Indústrias secundárias e serviços"},
        {"34308", "Regulamento do comércio ( incluindo garantias e warranties) Preços / Marketings/ Comodities / Publicidade"},
        {"343087", "Comércio internacional / Importação / Exportação"},
        {"34309", "Controle de empresas públicas"},
        {"343091", "Em geral"},
        {"343092", "Água e energia"},
        {"3430924", "Água"},
        {"3430925", "Energia nuclear"},
        {"3430926", "Petróleo e gás"},
        {"3430927", "Carvão"},
        {"3430928", "Energia solar"},
        {"3430929", "Energia elétrica"},
        {"343093", "Transporte ( incluindo oleodutos) Frete/ carga / passageiros / transporte de ambulância"},
        {"343094", "Transporte rodoviário ( estradas, veículos, transporte comercial, ônibus, caminhões )"},
        {"343095", "Transporte ferroviário ( linhas, estações ferroviárias, trens, vagões,                  serviços de passageiros e carga )"},
        {"343096", "Transporte marítimo / Em oceanos, rios, passageiros, carga , portos"},
        {"343097", "Transporte aéreo / aeroportos, cargas e passageiros, vôos espaciais"},
        {"343098", "Transporte local / ruas, pedestres, ônibus, metrôs, carros"},
        {"343099", "Comunicações / correio, telégrafo, telefone, computador, rádio, televisão, imprensa"},
        {"34339", "Jurisdições e áreas ( Tabela 2 ) Ex.: Impostos na Austrália: 343.0594"},
        {"344", "Direito trabalhista"},
        {"34402", "Seguro social(seguros de acidentes, doenças, maternidade,idade,etc)"},
        {"34403", "Serviço social (ajuda alimentação, instituições penais)"},
        {"344041", "Problemas sociais e serviços ( pessoal médico e suas atividades, dentistas, enfermeiros, farmacêuticos, problemas na prática damedicina incluindo aborto, experimentação com pessoas, eutanásia)"},
        {"344042", "Controle de produtos ( comida, remédios, produtos químicos) 344.043;Controle de doenças ( vacinação, campanhas, quarentena)"},
        {"344044", "Controle de doenças mentais e abuso de substâncias ( alcoolismo, drogas)"},
        {"344045", "Controle de mortalidade"},
        {"344046", "Proteção ao meio ambiente ( desperdício/ reciclagem/ poluição de rios, mares, terra, ar/ poluição sonora/ poluição industrial e sanitária)"},
        {"344047", "Segurança  (pública, divertimentos, recreação)"},
        {"344048", "Controle da população ( controle de natalidade )"},
        {"344049", "Controle público de saúde veterinária"},
        {"34405", "Controle policial / segurança pública em relação à moral e costumes"},
        {"34407", "Legislação em educação / Lei de Diretrizes e Bases / Legislação em relação a educação pública, particular currículos, estudantes, professores)"},
        {"34409", "Cultura e religião (Legislação referente a comemorações  históricas e patrióticas, bibliotecas, arquivos, museus e galerias, preservação de monumentos, ciência e tecnologia, religião, divertimentos)"},
        {"34439", "Jurisdições e áreas  ( Tabela 2 )                Ex.: Lei trabalhista  na Austrália:344.94"},
        {"345", "Direito penal"},
        {"34501", "Cortes criminais"},
        {"34502", "Crimes"},
        {"34503", "Criminosos ( incluindo delinqüência  juvenil)"},
        {"34504", "Responsabilidade, criminalidade ( capacidade de cometer crime, intenção, defesa )"},
        {"34505", "Procedimento criminal ( Processos, defesa, investigação, extradição,  assistência judicial, busca e captura, detenção, direitos ( incluindo habeas corpus)"},
        {"34506", "Evidencias, confissões"},
        {"34507", "Julgamentos, disposições finais ( perdão, reabilitação), sentenças, penas"},
        {"34508", "Tribunal do Júri / JURIS;Procedimentos, julgamentos, detenções, etc., para delinqüentes juvenis;Tribunal de Justiça"},
        {"34539", "Jurisdição e áreas ( Tabela 2 )       Ex.: Evidência criminal na Austrália :345.9406"},
        {"346", "Direito privado"},
        {"346001", "Teoria e filosofia"},
        {"346012", "Pessoas e relações domésticas ( capacidade, atributos, pessoas com doenças, grupos raciais, étnicos, econômicos, escravos, mulheres, menores (incluindo maioridade )"},
        {"346015", "Relações familiares, casamento, direitos dos cônjuges, divorcio, anulação separação"},
        {"346017", "Pais e filhos, parentesco, paternidade, adoção,  guarda, custódia, legitimação, direito de visitas)"},
        {"34602", "Contratos em geral, públicos, de serviço"},
        {"34603", "Responsabilidades (incluindo de empregados, diretores, hospitais) / negligencia ( acidentes e mortes) / delitos ( assaltos, invasões de privacidade, erro médico,  falso testemunho, difamação, delitos envolvendo propriedade ( fraude, incomodo)"},
        {"34604", "Propriedade ( terra, casa, direito de água, condomínios, aluguel, compra, venda, transferencia , etc.) Propriedade pública e privada"},
        {"346048", "Propriedade intangível ( propriedade intelectual, industrial, copyright, design, marcas)"},
        {"34605", "Herança, sucessão, custódia, testamento"},
        {"34606", "Organizações, associações, sociedades, fundações (criação,contabilidade, negócios, organização, direção, desenvolvimento, organizações governamentais)"},
        {"346064", "Legislação para criação e funcionamento de organizações não profissionais, de caridade, fundações, etc."},
        {"34607", "Direito comercial Legislação comercial (venda, empréstimo, apólices, fianças, créditos)"},
        {"34608", "Bancos e seguros"},
        {"34609", "Financeiras / instrumentos de segurança, garantia, aval, fiança, títulos e obrigações, valores, etc"},
        {"34639", "Jurisdição e áreas ( Tab.2 )  Ex.: Lei de propriedade na Austrália : 346.9404"},
        {"347", "Direito civil"},
        {"34701", "Tribunais"},
        {"347012", "Decisões / erros judiciais / revisões"},
        {"347013", "Administração das cortes"},
        {"347014", "Juizes"},
        {"347016", "Outros oficiais / oficiais de justiça "},
        {"347017", "Ajuda legal"},
        {"34702", "Tribunais ( municipais, estaduais, federais)"},
        {"34703", "Tribunais de apelação"},
        {"34704", "Tribunais com jurisdição especializada"},
        {"34705", "Procedimentos"},
        {"34706", "Evidencias ( admissibilidade, testemunhos)"},
        {"34708", "Procedimentos de apelação"},
        {"34709", "Debates, argumentações e resoluções,"},
        {"34739", "Jurisdições e áreas ( Tabela 2) Ex.:  Lei de evidências na Austrália:     347.9406"},
        {"348", "Legislação, regulamentos, causas"},
        {"34801", "Materiais preliminares ( audiências, históricos, estatísticas, relatórios, provas)"},
        {"34802", "Estatutos e regulamentos"},
        {"348023", "Códigos, seleção de leis , citações (incluindo arranjo em ordem alfabética)"},
        {"34804", "Pastas com relatórios e informativos nacionais, estaduais, regionais, decisões de tribunais"},
        {"34805", "Decisões de tribunais superiores"},
        {"34839", "Jurisdições e áreas ( Tabela 2 )  Ex.: Seleção de leis da Austrália : 348.94.024"}
    };

    public void importarCdd() throws Exception {
        for (int i = 0; i < tabelaInicial.length; i++) {
            area = new AreaConhecimento(
                    GeradorID.getProximoID(),
                    Integer.parseInt(tabelaInicial[i][0]),
                    tabelaInicial[i][1]
            );

            new ControleAreaConhecimento().incluir(area);
        }
    }
}