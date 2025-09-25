<template>
  <v-container>
    <v-row class="mb-6">
      <v-col cols="12">
        <h1 class="text-h5">CRUD de Produtos e Categorias Domésticos</h1>
        <p>Escolha uma seção para começar:</p>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12" md="6">
        <v-card class="hover:shadow-lg">
          <v-card-title>
            <v-icon start>mdi-package-variant</v-icon>
            Produtos
          </v-card-title>
          <v-card-text>
            Cadastre, edite e exclua produtos.
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn color="primary" :to="{ path: '/produtos' }">
              Ir para Produtos
              <v-icon end>mdi-arrow-right</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>

      <v-col cols="12" md="6">
        <v-card class="hover:shadow-lg">
          <v-card-title>
            <v-icon start>mdi-shape</v-icon>
            Categorias
          </v-card-title>
          <v-card-text>
            Cadastre, edite e exclua categorias.
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn color="primary" :to="{ path: '/categorias' }">
              Ir para Categorias
              <v-icon end>mdi-arrow-right</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
