package ru.rollsroll.database.product

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.rollsroll.database.tokens.TokenDTO

object Product: Table("tokens") {
    private val id = Product.varchar("id", 100)
    private val title = Product.varchar("title", 60)
    private val logo = Product.varchar("logo", 50)
    private val description = Product.varchar("description", 200)
    private val weight = Product.varchar("weight", 50)
    private val price = Product.varchar("price", 25)

    fun insert(productDTO: ProductDTO) {
        transaction {
            Product.insert{
                it[id] = productDTO.id
                it[title] = productDTO.title
                it[logo] = productDTO.logo
                it[description] = productDTO.description
                it[weight] = productDTO.weight
                it[price] = productDTO.price

            }
        }
    }
    fun fetchTokens(): List<ProductDTO>{
        return try {
            transaction {
                Product.selectAll().toList()
                    .map{
                        ProductDTO(
                           id = it[Product.id],
                            title = it[title],
                            logo = it[logo],
                            description = it[description],
                            weight = it[weight],
                            price = it[price],
                        )
                    }
            }
        } catch (e: Exception){
            emptyList()
        }
    }
}