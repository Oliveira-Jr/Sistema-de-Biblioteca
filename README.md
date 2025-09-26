# 📚 App de Gestão de Biblioteca Comunitária  

A aplicação tem como objetivo realizar a **gestão de livros, usuários e empréstimos** em uma biblioteca comunitária.  

## ✨ Funcionalidades  

- **Gestão de livros**:  
  - Cadastro de livros com título, autor, ano, gênero, identificador único e status (disponível, emprestado, reservado).  
  - Administração de reservas e controle do acervo.  

- **Gestão de usuários**:  
  - Cadastro de leitores e administradores.  
  - Leitores podem consultar o acervo, solicitar empréstimos, devolver livros e realizar reservas.  
  - Administradores podem cadastrar/remover livros, gerenciar usuários e gerar relatórios.  

- **Empréstimos**:  
  - Registro com data de retirada, prazo de devolução e data efetiva de entrega.  
  - Cálculo de multa em caso de atraso.  
  - Histórico de empréstimos para cada usuário.  

- **Reservas**:  
  - Associação de um usuário a um livro específico.  
  - Opções de confirmação ou cancelamento.  

- **Relatórios**:  
  - Livros mais emprestados.  
  - Usuários mais ativos.  
  - Estado atual do acervo (quantos disponíveis, emprestados ou reservados).  

## 🔧 Flexibilidade  

Os livros implementam a classe abstrata `Emprestavel`, que define os comportamentos e atributos básicos de empréstimo e devolução, permitindo que futuramente outros tipos de itens (revistas, DVDs, etc.) possam ser integrados ao sistema de forma uniforme.  
