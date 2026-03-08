package com.droid.ffdpboss.data


import com.droid.data.model.betModel.BettingDraftList
import com.droid.ffdpboss.R
import com.droid.ffdpboss.data.model.BetModel
import com.droid.ffdpboss.ui.theme.PColor
import com.droid.ffdpboss.ui.theme.Purple40
import com.droid.ffdpboss.ui.theme.game1Color
import com.droid.ffdpboss.ui.theme.game2Color
import com.droid.ffdpboss.ui.theme.game3Color
import com.droid.ffdpboss.ui.theme.textColor

fun getSeviceBannerStaticData():List<GameListData>{
    val serviceList =listOf(
        GameListData( Purple40,PColor,"Kolkata Fatafat","Kolkata Fatafat","12.00","20.00"),
        GameListData( Purple40, game1Color,"Kolkata Fatafat","Kolkata Fatafat","12.00","20.00"),
        GameListData(Purple40, game2Color,"Kadamtala Fatafat","Kolkata Fatafat","12.00","20.00"),
        GameListData(Purple40, game3Color,"Howrah Fatafat","Kolkata Fatafat","12.00","20.00"),
        GameListData(Purple40, game1Color,"Ramtala Fatafat","Kolkata Fatafat","12.00","20.00"),
        GameListData(Purple40, game2Color,"Kolkata Fatafat","Kolkata Fatafat","12.00","20.00"),
        GameListData(textColor, game3Color,"Kolkata Fatafat","Kolkata Fatafat","12.00","20.00"),
        GameListData(PColor, Purple40,"Kolkata Fatafat","Kolkata Fatafat","12.00","20.00")
    )
    return serviceList
}

fun getGameStaticData():List<GameTypeModel>{
    val gameList = listOf(
        GameTypeModel("Single", R.drawable.single_patti),
        GameTypeModel("Patti", R.drawable.tin_patti),
        GameTypeModel("Double", R.drawable.sp_k),
        GameTypeModel("CP", R.drawable.cp),
    )
return gameList
}
data class GameTypeModel(val name:String,val image:Int)


fun getBetAction(): MutableList<BetModel> {
    val betList = mutableListOf(
        BetModel("142", "2000", "Yes"),
        BetModel("963", "2800", "Yes"),
       // BetModel("744", "7840", "Yes"),
       // BetModel("781", "800", "Yes"),
    )
    return betList
}
fun getBetTitle():List<BettingDraftList>{
    val betList = listOf(
        BettingDraftList("Digit","Amount","Action")
    )
    return betList
}