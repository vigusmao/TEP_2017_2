def gerar_permutacoes(n):

    def backtrack(estado, usado):
        # testa o estado corrente: preciso fazer algo especial?
        if len(estado) == n:
            print(estado)
            return
        
        for candidato in range(1, n+1):
            if usado[candidato]:
                continue
            
            # expande o estado corrente
            estado.append(candidato)
            usado[candidato] = True

            # chama recursivamente
            backtrack(estado, usado)

            # desfaz as alteracoes, preparando para a proxima iteracao
            estado.pop()
            usado[candidato] = False
    # ------

    # inicializa as estruturas que representam o estado corrente
    estado = []
    usado = [False] * (n+1)

    # faz a chamada original a funcao recursiva
    backtrack(estado, usado)
    
# -----------


# Main

while (True):
    n = eval(input("Digite o valor de n (0 para sair): "))
    if n == 0:
        break
    gerar_permutacoes(n)
    print()
