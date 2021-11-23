package com.clocktime.severalwest.ui.choosing_wallpaper

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.clocktime.severalwest.R
import com.clocktime.severalwest.databinding.FragmentChoosingWallpaperBinding
import com.clocktime.severalwest.model.StyleBg
import com.clocktime.severalwest.store.PreferenceStoreImpl
import com.clocktime.severalwest.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChoosingWallpaperFragment :
    BaseFragment<FragmentChoosingWallpaperBinding>(R.layout.fragment_choosing_wallpaper) {

    private var actualPicture = 0
    private lateinit var actualImage: ImageButton
    private val wallpaperList = arrayListOf(
        R.drawable.ic_choosing_wallpaper_one,
        R.drawable.ic_choosing_wallpaper_two,
        R.drawable.ic_choosing_wallpaper_three,
        R.drawable.ic_choosing_wallpaper_four,
        R.drawable.ic_choosing_wallpaper_five,
        R.drawable.ic_choosing_wallpaper_six,
        R.drawable.ic_choosing_wallpaper_seven,
        R.drawable.ic_choosing_wallpaper_eight,
        R.drawable.ic_choosing_wallpaper_nine
    )

    @Inject
    lateinit var preferenceStoreImpl: PreferenceStoreImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        with(binding) {
            when (preferenceStoreImpl.styleBg) {
                StyleBg.BG_ONE -> changeWallpaper(
                    choosingWallpaperOneImgBtn,
                    0,
                    R.drawable.ic_choosing_wallpaper_one_border
                )
                StyleBg.BG_TWO -> changeWallpaper(
                    choosingWallpaperTwoImgBtn,
                    1,
                    R.drawable.ic_choosing_wallpaper_two_border
                )
                StyleBg.BG_THREE -> changeWallpaper(
                    choosingWallpaperThreeImgBtn,
                    2,
                    R.drawable.ic_choosing_wallpaper_three_border
                )
                StyleBg.BG_FOUR -> changeWallpaper(
                    choosingWallpaperFourImgBtn,
                    3,
                    R.drawable.ic_choosing_wallpaper_four_border
                )
                StyleBg.BG_FIVE -> changeWallpaper(
                    choosingWallpaperFiveImgBtn,
                    4,
                    R.drawable.ic_choosing_wallpaper_five_border
                )
                StyleBg.BG_SIX -> changeWallpaper(
                    choosingWallpaperSixImgBtn,
                    5,
                    R.drawable.ic_choosing_wallpaper_six_border
                )
                StyleBg.BG_SEVEN -> changeWallpaper(
                    choosingWallpaperSevenImgBtn,
                    6,
                    R.drawable.ic_choosing_wallpaper_seven_border
                )
                StyleBg.BG_EIGHT -> changeWallpaper(
                    choosingWallpaperEightImgBtn,
                    7,
                    R.drawable.ic_choosing_wallpaper_eight_border
                )
                StyleBg.BG_NINE -> changeWallpaper(
                    choosingWallpaperNineImgBtn,
                    8,
                    R.drawable.ic_choosing_wallpaper_nine_border
                )
            }

            choosingWallpaperOneImgBtn.setOnClickListener {
                choosingWallpaper(
                    choosingWallpaperOneImgBtn,
                    0,
                    StyleBg.BG_ONE,
                    R.drawable.ic_choosing_wallpaper_one_border
                )
            }

            choosingWallpaperTwoImgBtn.setOnClickListener {
                choosingWallpaper(
                    choosingWallpaperTwoImgBtn,
                    1,
                    StyleBg.BG_TWO,
                    R.drawable.ic_choosing_wallpaper_two_border
                )
            }

            choosingWallpaperThreeImgBtn.setOnClickListener {
                choosingWallpaper(
                    choosingWallpaperThreeImgBtn,
                    2,
                    StyleBg.BG_THREE,
                    R.drawable.ic_choosing_wallpaper_three_border
                )
            }

            choosingWallpaperFourImgBtn.setOnClickListener {
                choosingWallpaper(
                    choosingWallpaperFourImgBtn,
                    3,
                    StyleBg.BG_FOUR,
                    R.drawable.ic_choosing_wallpaper_four_border
                )
            }

            choosingWallpaperFiveImgBtn.setOnClickListener {
                choosingWallpaper(
                    choosingWallpaperFiveImgBtn,
                    4,
                    StyleBg.BG_FIVE,
                    R.drawable.ic_choosing_wallpaper_five_border
                )
            }

            choosingWallpaperSixImgBtn.setOnClickListener {
                choosingWallpaper(
                    choosingWallpaperSixImgBtn,
                    5,
                    StyleBg.BG_SIX,
                    R.drawable.ic_choosing_wallpaper_six_border
                )
            }

            choosingWallpaperSevenImgBtn.setOnClickListener {
                choosingWallpaper(
                    choosingWallpaperSevenImgBtn,
                    6,
                    StyleBg.BG_SEVEN,
                    R.drawable.ic_choosing_wallpaper_seven_border
                )
            }

            choosingWallpaperEightImgBtn.setOnClickListener {
                choosingWallpaper(
                    choosingWallpaperEightImgBtn,
                    7,
                    StyleBg.BG_EIGHT,
                    R.drawable.ic_choosing_wallpaper_eight_border
                )
            }

            choosingWallpaperNineImgBtn.setOnClickListener {
                choosingWallpaper(
                    choosingWallpaperNineImgBtn,
                    8,
                    StyleBg.BG_NINE,
                    R.drawable.ic_choosing_wallpaper_nine_border
                )
            }
        }
    }

    private fun changeWallpaper(image: ImageButton, index: Int, picture: Int) {
        image.setBackgroundResource(picture)
        actualImage = image
        actualPicture = index
    }

    private fun anim(
        mainImg: ImageButton,
        twoImg: ImageButton,
        mainPicture: Int,
        twoPicture: Int,
    ) {
        mainImg.setBackgroundResource(mainPicture)
        twoImg.setBackgroundResource(twoPicture)
    }

    private fun choosingWallpaper(image: ImageButton, index: Int, style: StyleBg, picture: Int) {
        anim(
            image,
            actualImage,
            picture,
            wallpaperList[actualPicture]
        )
        preferenceStoreImpl.styleBg = style
        actualPicture = index
        actualImage = image
    }
}