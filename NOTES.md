# Damas

## Classes pensadas
- Core
> Classe principal do código, responsável por determinar como as demais classes serão iniciadas e finalizadas

- Moves
> Classe responsável por validar, buscar e realizar jogadas.

- LayoutFactory
> Classe responsável pelo posicionamento dos componentes no panel principal.  Precisa estar conectada com ComponentsFactory

- ComponentsFactory
> Classe responsável por iniciar e configurar os componentes do panel principal.


## Tabuleiro

O tabuleiro pode ser representado por um array bidimensional de JButtons contendo 8 linhas e 8 colunas. Assim, podemos adicionar o mesmo listener para todas as posições do array.
As cores das posições do tabuleiro podem ser determinadas utilizando um array da classe Color que contenha somente as duas cores padrões do tabuleiro. Ao final da iteração de cada linha, o array de cores deve ser revertido, formando a estética quadriculado do tabuleiro.

## Determinação de jogada

Podemos utilizar uma variável que contenha o número identificador do jogador da vez.
Nesse viés, cada peça de cor distinta deve ser nomeada com o número identificador de seu jogador (se houver como identificar imagens no botão, esta linha pode sofrer modificações)
Ao fim de cada jogada, um método pode ser chamado para alterar a vez do jogador.

## Movimento simples

``` Pseudocódigo
inicioalgoritimo
	Se há uma peça nesta posição então
		Se esta peça pode ser movida pelo jogador atual então
			Se a lista de posições jogáveis estiver preenchida então
				Para cada de lista de posições jogáveis faça
					Mude o background para preto
				Fimpara
				Limpe lista
			Senão
				Crie uma lista com possíveis jogadas para esta posição [[linha-1][coluna-1], [linha-1][coluna+1], [linha+1][coluna-1] e [linha+1][coluna+1]]
				Para cada de lista de posições jogáveis faça
					Se botão na posição determinada não possue peça então
						Mude o background de  botão na posição determinada para verde
					Fimse
				Fimpara
			Fimse
		Fimse
		Senão
			Se botão desta posição consta em lista com possíveis jogadas então
				Mova a imagem do botão anteriormente clicado para este
				Para cada de lista de posições jogáveis faça
					Mude o background para preto
				Fimpara
				Alterne a vez do jogador
			Fimse
		Fimse
Fimalgoritmo
```
	
