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
            <v-card 
              color="blue" 
              variant="flat" 
              class="pa-3 cursor-pointer"
              @click="$router.push('/produtos')"
              hover
            >
              <div class="text-white text-center">
                IR PARA PRODUTOS
                <v-icon end>mdi-arrow-right</v-icon>
              </div>
            </v-card>
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
            <v-card 
              color="blue" 
              variant="flat" 
              class="pa-3 cursor-pointer"
              @click="$router.push('/categorias')"
              hover
            >
              <div class="text-white text-center">
                IR PARA CATEGORIAS
                <v-icon end>mdi-arrow-right</v-icon>
              </div>
            </v-card>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.cursor-pointer {
  cursor: pointer;
  transition: all 0.2s ease-in-out;
}

.cursor-pointer:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(33, 150, 243, 0.3);
}
</style>
