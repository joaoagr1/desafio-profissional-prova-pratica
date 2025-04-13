# 🧙‍♂️ API de Personagens e Itens Mágicos

Esta API permite criar, atualizar, deletar e interagir com personagens e seus itens mágicos.
(Construção de testes unitários em progresso)

## 🌐 Base URL

```
http://localhost:8080
```

---

## 📘 Endpoints

### 🔹 Personagem

#### 🔍 Obter todos os personagens
`GET /personagem`

#### 🔍 Obter personagem por ID
`GET /personagem/{id}`

#### 🆕 Criar novo personagem
`POST /personagem`  
**Body:**
```json
{
  "nome": "string",
  "nomeAventureiro": "string",
  "classe": "GUERREIRO | MAGO | ARQUEIRO | LADINO | BARDO",
  "level": 1,
  "forcaBase": 0,
  "defesaBase": 0
}
```

#### 📝 Atualizar personagem
`PUT /personagem/{id}`  
**Body:** igual ao `POST /personagem`

#### ❌ Deletar personagem
`DELETE /personagem/{id}`

#### ➕ Adicionar item mágico a um personagem
`POST /personagem/add-item/{personagemId}`  
**Body:**
```json
{
  "nome": "string",
  "tipo": "ARMA | ARMADURA | AMULETO",
  "forca": 0,
  "defesa": 0
}
```

#### 🧿 Buscar amuleto do personagem
`GET /personagem/buscar-amuleto/{personagemId}`

---

### 🔸 Item Mágico

#### 🔍 Obter todos os itens mágicos
`GET /item-magico`

#### 🔍 Obter item mágico por ID
`GET /item-magico/{id}`

#### 🆕 Criar novo item mágico
`POST /item-magico`  
**Body:** igual ao `POST /personagem/add-item/{id}`

#### 📝 Atualizar item mágico
`PUT /item-magico/{id}`

#### ❌ Deletar item mágico
`DELETE /item-magico/{id}`

---

### 🔻 Remover item mágico de um personagem

`DELETE /item-magico/{personagemId}/{itemId}`  
Remove um item mágico específico do personagem.

---

## 📦 Esquemas (Schemas)

### 🔹 `Personagem`
```json
{
  "id": 1,
  "nome": "string",
  "nomeAventureiro": "string",
  "classe": "GUERREIRO",
  "level": 1,
  "forcaBase": 5,
  "defesaBase": 5,
  "forcaTotal": 10,
  "defesaTotal": 8,
  "itensMagicos": []
}
```

### 🔸 `ItemMagico`
```json
{
  "id": 1,
  "nome": "Espada Flamejante",
  "tipo": "ARMA",
  "forca": 5,
  "defesa": 0
}
```

---

## 🧪 Teste com Swagger UI

Acesse a interface Swagger em:

```
http://localhost:8080/swagger-ui.html
```

ou

```
http://localhost:8080/swagger-ui/index.html
```
